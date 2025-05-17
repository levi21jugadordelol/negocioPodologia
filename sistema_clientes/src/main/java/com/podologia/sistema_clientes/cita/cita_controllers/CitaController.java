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
@RequestMapping("citas")
public class CitaController {

    private final ICitaService citaService;

    // Crear una cita
    @PostMapping("/crear")
    public ResponseEntity<String> saveCita(@RequestBody CitaEntity cita) {
        citaService.saveCita(cita);
        return ResponseEntity.status(HttpStatus.CREATED).body("CITA CREADA");
    }

    // Traer todas las citas
    @GetMapping("/todas")
    public ResponseEntity<List<CitaEntity>> traerTodasLasCitas() {
        List<CitaEntity> citas = citaService.getCita();
        if (citas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(citas);
    }

    // Buscar una cita por ID
    //Usa ResponseEntity<?> cuando devuelves más de un tipo de cuerpo (CitaEntity o String). Es una práctica común en controladores REST para manejar tanto respuestas exitosas como errores sin forzar conversiones.
    @GetMapping("/{idCita}")
    public ResponseEntity<CitaEntity> buscarCitaPorId(@PathVariable Long idCita) {
        CitaEntity cita = citaService.findCita(idCita).get();
        return  ResponseEntity.ok(cita);
    }


    // Buscar citas por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CitaEntity>> buscarCitasPorClienteId(@PathVariable Long clienteId) {
        List<CitaEntity> citas = citaService.buscarCitasPorClienteId(clienteId);
        return ResponseEntity.ok(citas);
    }


    // Editar una cita
    @PutMapping("/editar/{idCita}")
    public ResponseEntity<String> editarCita(@PathVariable Long idCita, @RequestBody CitaEntity nuevaCita) {
        log.info("el id de la cita a editar es : "+idCita);
        citaService.editCita(idCita, nuevaCita);
        return ResponseEntity.ok("Cita editada");
    }

    // Eliminar una cita
    @DeleteMapping("/eliminar/{idCita}")
    public ResponseEntity<String> eliminarCita(@PathVariable Long idCita) {
        citaService.deleteCita(idCita);
        return ResponseEntity.noContent().build();
    }
}

