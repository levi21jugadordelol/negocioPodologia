package com.podologia.sistema_clientes.cita.cita_controllers;

import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.cita.cita_service.ICitaService;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Slf4j
//RequestMapping("/citas")
public class CitaController {

    private final ICitaService citaService;

    // Crear una cita
    @PostMapping("citas/crear")
    public ResponseEntity<String> saveCita(@RequestBody CitaEntity cita) {
        citaService.saveCita(cita);
        return ResponseEntity.status(HttpStatus.CREATED).body("CITA CREADA");
    }

    // Traer todas las citas
    @GetMapping("citas/todas")
    public ResponseEntity<List<CitaEntity>> traerTodasLasCitas() {
        List<CitaEntity> citas = citaService.getCita();
        if (citas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(citas);
        }
        return ResponseEntity.status(HttpStatus.OK).body(citas);
    }

    // Buscar una cita por ID
    //Usa ResponseEntity<?> cuando devuelves más de un tipo de cuerpo (CitaEntity o String). Es una práctica común en controladores REST para manejar tanto respuestas exitosas como errores sin forzar conversiones.
    @GetMapping("citas/{idCita}")
    public ResponseEntity<?> buscarCitaPorId(@PathVariable Long idCita) {
        Optional<CitaEntity> optional = citaService.findCita(idCita);
        if (optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CITA NO ENCONTRADA");
        }
    }


    // Buscar citas por cliente
    @GetMapping("cita/cliente/{clienteId}")
    public ResponseEntity<Object> buscarCitasPorClienteId(@PathVariable Long clienteId) {
        List<CitaEntity> citas = citaService.buscarCitasPorClienteId(clienteId);
        if (citas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("SIN CITAS PARA ESTE CLIENTE");
        }
        return ResponseEntity.status(HttpStatus.OK).body(citas);
    }

    // Editar una cita
    @PutMapping("cita/editar/{idCita}")
    public ResponseEntity<String> editarCita(@PathVariable Long idCita, @RequestBody CitaEntity nuevaCita) {
        citaService.editCita(idCita, nuevaCita);
        return ResponseEntity.status(HttpStatus.OK).body("CITA EDITADA");
    }

    // Eliminar una cita
    @DeleteMapping("cita/eliminar/{idCita}")
    public ResponseEntity<String> eliminarCita(@PathVariable Long idCita) {
        citaService.deleteCita(idCita);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("CITA ELIMINADA");
    }
}

