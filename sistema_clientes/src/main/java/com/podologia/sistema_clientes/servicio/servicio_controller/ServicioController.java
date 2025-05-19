package com.podologia.sistema_clientes.servicio.servicio_controller;


import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
import com.podologia.sistema_clientes.servicio.servicio_service.IServicioService;
import com.podologia.sistema_clientes.servicio.servicio_service.ServicioServiceImpl;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;
import com.podologia.sistema_clientes.shared.exception.ValidacionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicios")
@RequiredArgsConstructor

public class ServicioController {

    private final IServicioService servicioService;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.servicio.servicio_controller.ServicioController.class);

    @PostMapping("/crear")
    public ResponseEntity<?> crearServicio(@RequestBody ServicioEntity servicio) {
        log.info("Recibiendo solicitud para crear servicio: {}", servicio);
        try {
            ServicioEntity guardado = servicioService.saveServicio(servicio);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (ValidacionException | IllegalArgumentException ex) {
            log.warn("Validación fallida al crear servicio: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarServicios() {
        List<ServicioEntity> servicios = servicioService.getServicios();
        if (servicios.isEmpty()) {
            log.warn("Lista de servicios vacía");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servicios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            ServicioEntity servicio = servicioService.findServicio(id).orElse(null);
            return ResponseEntity.ok(servicio);
        } catch (EntidadNoEncontradaException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarServicio(@PathVariable Long id, @RequestBody ServicioEntity nuevosDatos) {
        try {
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

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {
        try {
            ServicioEntity servicio = servicioService.buscarNombre(nombre).orElse(null);
            return ResponseEntity.ok(servicio);
        } catch (EntidadNoEncontradaException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}

