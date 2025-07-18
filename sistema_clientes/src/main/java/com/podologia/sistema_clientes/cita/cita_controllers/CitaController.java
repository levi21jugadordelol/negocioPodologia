package com.podologia.sistema_clientes.cita.cita_controllers;

import com.podologia.sistema_clientes.cita.cita_dtos.CitaDto;
import com.podologia.sistema_clientes.cita.cita_dtos.CitaRequestDto;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.cita.cita_service.ICitaService;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.detalleCita.detalle_dtos.DetalleDto;
import com.podologia.sistema_clientes.detalleCita.detalle_dtos.DetalleRequestDto;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.enume.EstadoCita;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
import com.podologia.sistema_clientes.shared.mappers.CitaMapper;
import com.podologia.sistema_clientes.shared.mappers.DetalleMapper;
import com.podologia.sistema_clientes.shared.metodoValidaciones.ValidacionCita;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController

@RequestMapping("/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    private final ICitaService citaService;
    private  final ValidacionCita validacionCita;
    private final CitaMapper citaMapper;
    private final DetalleMapper detalleMapper;

    private static final Logger log = LoggerFactory.getLogger( com.podologia.sistema_clientes.cita.cita_controllers.CitaController.class);

    // Crear una cita
    @PostMapping("/crear")
    public ResponseEntity<?> saveCita(@RequestBody CitaRequestDto citaRequestDto) {
        log.debug("CitaRequestDTO recibida en el controller: {}", citaRequestDto);

        // Mapea de DTO a Entity
        CitaEntity citaEntity = citaMapper.toCitaEntity(citaRequestDto);

        // Asignar manualmente el cliente basado en clienteId
        if (citaRequestDto.getClienteId() != null) {
            ClienteEntity cliente = new ClienteEntity();
            cliente.setIdCliente(citaRequestDto.getClienteId());
            citaEntity.setCliente(cliente);
        } else {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "clienteId es obligatorio")
            );
        }

        // Guardar la cita
        CitaEntity citaGuardada = citaService.saveCita(citaEntity);

        // Respuesta con JSON válido
        return ResponseEntity.ok(
                Map.of("mensaje", "Cita guardada exitosamente",
                        "idCita", citaGuardada.getIdCita())
        );
    }


    //crear detalle

    @PostMapping("/{idCita}/detalles")
    public ResponseEntity<?> saveDetalles(@PathVariable Long idCita,@RequestBody DetalleRequestDto detalleRequestDto) {
        log.debug("Detalle recibido para la cita {}: {}", idCita, detalleRequestDto);

        // Mapear DTO a entidad
        DetalleEntity detalleEntity =  detalleMapper.toDetalleEntity(detalleRequestDto);

        // Guardar la entidad
        DetalleEntity detalleGuardado = citaService.saveDetalle(idCita, detalleEntity);

        // Mapear entidad guardada a DTO de respuesta
        DetalleDto detalleDto = detalleMapper.toDetalleDto(detalleGuardado);

       // return ResponseEntity.status(HttpStatus.CREATED).body(detalleGuardado);

        return ResponseEntity.status(HttpStatus.CREATED).body(detalleDto);

    }

    // Traer todas las citas
    @GetMapping("/todas")
    public ResponseEntity<List<CitaDto>> traerTodasLasCitas() {
        List<CitaEntity> citas = citaService.getCita();
        if (citas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        // Mapea lista de Entities a lista de DTOs de respuesta

        List<CitaDto> citaResponse = citas.stream()
                .map(citaMapper::toCitaDto)
                .collect(Collectors.toList());


        return ResponseEntity.ok(citaResponse);
    }

    // Buscar una cita por ID
    //Usa ResponseEntity<?> cuando devuelves más de un tipo de cuerpo (CitaEntity o String). Es una práctica común en controladores REST para manejar tanto respuestas exitosas como errores sin forzar conversiones.
    @GetMapping("/{idCita}")
    public ResponseEntity<CitaDto> buscarCitaPorId(@PathVariable Long idCita) {
        CitaEntity cita = citaService.findCita(idCita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada"));

        CitaDto citaDto = citaMapper.toCitaDto(cita);

        return ResponseEntity.ok(citaDto);
    }


    @GetMapping("/clientes")
    public ResponseEntity<List<CitaDto>> obtenerCitasFiltradas(
            @RequestParam EstadoCita estado,
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String nombre) {
        List<CitaDto> resultado = citaService.filtrarCitas(estado, dni, nombre);
        log.info("Los clientes con estado {} son: {}", estado, resultado);
        return ResponseEntity.ok(resultado);

    }



    // Buscar citas por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CitaDto>> buscarCitasPorClienteId(@PathVariable Long clienteId) {
        List<CitaEntity> citas = citaService.buscarCitasPorClienteId(clienteId);

        List<CitaDto> citaResponse = citas.stream()
                .map(citaMapper::toCitaDto)
                .collect(Collectors.toList());


        return ResponseEntity.ok(citaResponse);
    }


    @GetMapping("/estados")
    public List<Map<String, String>> obtenerEstadosCita() {
        List<Map<String, String>> estados = Arrays.stream(EstadoCita.values())
                .map(e -> Map.of(
                        "codigo", e.name(),
                        "valor", e.getEstadoCita()
                ))
                .collect(Collectors.toList());

        log.info("Estados de cita disponibles: {}", estados);

        return estados;
    }



    // Editar una cita
    @PutMapping("/editar/{idCita}")
    public ResponseEntity<Map<String, Object>> editarCita(@PathVariable Long idCita, @RequestBody CitaRequestDto nuevaCitaDto) {
        log.info("el id de la cita a editar es : "+idCita);

        log.info("DTO recibido completo: {}", nuevaCitaDto);
        log.info("Cliente dentro del DTO: {}", nuevaCitaDto.getClienteId());

      //  CitaEntity citaEntity = citaMapper.toCitaEntity(nuevaCitaDto);

        citaService.editCita(idCita, nuevaCitaDto);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Cita editada");
        respuesta.put("id_cita", idCita);

        return ResponseEntity.ok(respuesta);
    }



    // Eliminar una cita
    @DeleteMapping("/eliminar/{idCita}")
    public ResponseEntity<String> eliminarCita(@PathVariable Long idCita) {
        citaService.deleteCita(idCita);
        return ResponseEntity.noContent().build();
    }


    //endpint test para que cita obtenga sus servicios
    @GetMapping("/{id}/servicio")
    public ResponseEntity <ServicioEntity> getServicioDePrimeraCita(@PathVariable Long id) {
        Optional<ServicioEntity> servicioOptional = citaService.obtenerServicioPorIdCita(id);

        return servicioOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/por-dia")
    public ResponseEntity<List<CitaDto>> obtenerCitasPorFecha(@RequestParam("fecha") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)LocalDate fecha){
        List<CitaEntity> citasDelDia = citaService.guardarCitaPorDia(fecha);
        List<CitaDto> citasDtos = citasDelDia.stream().map(citaMapper::toCitaDto).toList();
        return ResponseEntity.ok(citasDtos);
    }


}

