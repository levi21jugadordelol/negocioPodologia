package com.podologia.sistema_clientes.cita.cita_controllers;

import com.podologia.sistema_clientes.cita.cita_dtos.CitaDto;
import com.podologia.sistema_clientes.cita.cita_dtos.CitaRequestDto;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.cita.cita_service.ICitaService;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.detalleCita.detalle_dtos.DetalleDto;
import com.podologia.sistema_clientes.detalleCita.detalle_dtos.DetalleRequestDto;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.shared.mappers.CitaMapper;
import com.podologia.sistema_clientes.shared.mappers.DetalleMapper;
import com.podologia.sistema_clientes.shared.metodoValidaciones.ValidacionCita;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController

@RequestMapping("citas")
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
        // 2. Asignar manualmente el cliente basado en clienteId
        if (citaRequestDto.getClienteId() != null) {
            ClienteEntity cliente = new ClienteEntity();
            cliente.setIdCliente(citaRequestDto.getClienteId());
            citaEntity.setCliente(cliente);
        } else {
            // Aquí podrías lanzar excepción o devolver error si clienteId es obligatorio
            return ResponseEntity.badRequest().body("clienteId es obligatorio");
        }

        // 3. Guardar la cita
        citaService.saveCita(citaEntity);

        return ResponseEntity.ok().build();

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

        return ResponseEntity.status(HttpStatus.CREATED).body(detalleGuardado);

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


    // Buscar citas por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CitaDto>> buscarCitasPorClienteId(@PathVariable Long clienteId) {
        List<CitaEntity> citas = citaService.buscarCitasPorClienteId(clienteId);

        List<CitaDto> citaResponse = citas.stream()
                .map(citaMapper::toCitaDto)
                .collect(Collectors.toList());


        return ResponseEntity.ok(citaResponse);
    }


    // Editar una cita
    @PutMapping("/editar/{idCita}")
    public ResponseEntity<String> editarCita(@PathVariable Long idCita, @RequestBody CitaRequestDto nuevaCitaDto) {
        log.info("el id de la cita a editar es : "+idCita);

        log.info("DTO recibido completo: {}", nuevaCitaDto);
        log.info("Cliente dentro del DTO: {}", nuevaCitaDto.getClienteId());

      //  CitaEntity citaEntity = citaMapper.toCitaEntity(nuevaCitaDto);

        citaService.editCita(idCita, nuevaCitaDto);

        return ResponseEntity.ok("Cita editada");
    }

    // Eliminar una cita
    @DeleteMapping("/eliminar/{idCita}")
    public ResponseEntity<String> eliminarCita(@PathVariable Long idCita) {
        citaService.deleteCita(idCita);
        return ResponseEntity.noContent().build();
    }


}

