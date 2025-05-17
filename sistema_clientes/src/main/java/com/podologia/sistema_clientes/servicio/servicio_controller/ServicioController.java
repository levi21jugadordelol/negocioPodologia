package com.podologia.sistema_clientes.servicio.servicio_controller;


import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
import com.podologia.sistema_clientes.servicio.servicio_service.IServicioService;
import com.podologia.sistema_clientes.servicio.servicio_service.ServicioServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("servicio")
public class ServicioController {

    private final IServicioService servicioService;

    private final ICitaRepo citaRepo;

    @PostMapping("/crear")
    public ResponseEntity<?> crearServicio(@RequestBody ServicioEntity servicio) {
        log.info("Recibiendo servicio: {}", servicio);

        try {
            // Validar antes de guardar
            servicioService.validarDetalles(servicio); // ✅ Aquí sí sirve

            ServicioEntity guardado = servicioService.saveServicio(servicio); // guarda si todo está bien
            return ResponseEntity.ok(guardado);
        } catch (IllegalArgumentException ex) {
            log.warn("Validación fallida: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


}
