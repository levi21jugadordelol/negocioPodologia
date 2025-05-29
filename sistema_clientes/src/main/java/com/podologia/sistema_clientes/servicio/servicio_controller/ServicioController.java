package com.podologia.sistema_clientes.servicio.servicio_controller;


import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import com.podologia.sistema_clientes.servicio.servicio_dtos.ServicioDto;
import com.podologia.sistema_clientes.servicio.servicio_dtos.ServicioRequestDto;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
import com.podologia.sistema_clientes.servicio.servicio_service.IServicioService;
import com.podologia.sistema_clientes.servicio.servicio_service.ServicioServiceImpl;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;
import com.podologia.sistema_clientes.shared.exception.ValidacionException;
import com.podologia.sistema_clientes.shared.mappers.ServicioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicio")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ServicioController {

    private final IServicioService servicioService;
    private final ServicioMapper servicioMapper;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.servicio.servicio_controller.ServicioController.class);

    @PostMapping("/crear")
    public ResponseEntity<?> crearServicio(@RequestBody ServicioRequestDto servicioRequestDto) {
        log.info("Recibiendo solicitud para crear servicio: {}", servicioRequestDto);
        try {
            ServicioEntity entity = servicioMapper.toServicioEntity(servicioRequestDto);
            ServicioEntity guardado = servicioService.saveServicio(entity);
            ServicioDto responseDto = servicioMapper.toServicioDto(guardado);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (ValidacionException | IllegalArgumentException ex) {
            log.warn("Validación fallida al crear servicio: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/listarServicios")
    public ResponseEntity<?> listarServicios() {
        List<ServicioEntity> servicios = servicioService.getServicios();
        if (servicios.isEmpty()) {
            log.warn("Lista de servicios vacía");
            return ResponseEntity.noContent().build();
        }

        List<ServicioDto> dtoList = servicios.stream()
                .map(servicioMapper::toServicioDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
      return servicioService.findServicio(id)
              .map(servicio -> ResponseEntity.ok(servicioMapper.toServicioDto(servicio)))
              .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarServicio(@PathVariable Long id, @RequestBody ServicioRequestDto servicioRequestDto) {
        try {
            ServicioEntity nuevosDatos = servicioMapper.toServicioEntity(servicioRequestDto);
            servicioService.editServicio(id, nuevosDatos);
            return ResponseEntity.ok("Servicio actualizado correctamente");
        } catch (EntidadNoEncontradaException | ValidacionException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarServicio(@PathVariable Long id) {
        try {
            servicioService.deleteServicio(id);
            return ResponseEntity.ok("Servicio eliminado correctamente");
        } catch (EntidadNoEncontradaException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/buscarNombre")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {
        try {
            ServicioEntity servicio = servicioService.buscarNombre(nombre).orElse(null);
            return ResponseEntity.ok(servicio);
        } catch (EntidadNoEncontradaException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/buscarPorIdCita/{idCita}")
    public ResponseEntity<?> buscarServicioPorIdCita(@PathVariable Long idCita) {
        try {
            ServicioEntity servicio = servicioService.obtenerServicioPorIdCita(idCita)
                    .orElse(null);

            if (servicio == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró un servicio para la cita con ID: " + idCita);
            }

            ServicioDto servicioDto = servicioMapper.toServicioDto(servicio);
            return ResponseEntity.ok(servicioDto);
        } catch (EntidadNoEncontradaException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

}

