package com.podologia.sistema_clientes.cita.cita_service;

import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.cliente.IClienteRepo;
import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteDto;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.cliente.cliente_service.IClienteService;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CitaServiceImpl implements ICitaService {

    private final ICitaRepo citaRepo;



    @Override
    public List<CitaEntity> getCita() {
        List<CitaEntity> listaCitas = citaRepo.findAll();
        log.info("el total de citas es : {}",listaCitas.size());
        return listaCitas;
    }

    @Transactional
    @Override
    public void saveCita(CitaEntity citaEntity) {
            if(citaEntity == null){
                log.error("no puede ser nullo en el objeto cita");
                throw new IllegalArgumentException("cita no puede ser null");
            }
        citaRepo.save(citaEntity);
        log.info("cita guardada con éxito: {}", citaEntity);
    }

    @Transactional
    @Override
    public void deleteCita(Long id_cita) {
        if(!citaRepo.existsById(id_cita)){
            log.error("no existe dicho id {}", id_cita);
            throw new EntidadNoEncontradaException("Cita con ID " + id_cita + " no existe.");

        }else{
            citaRepo.deleteById(id_cita);
            log.info("el id fue eliminado correctamente: {}",id_cita);
        }
    }

    @Override
    public Optional<CitaEntity> findCita(long id_cita) {
      CitaEntity cita = citaRepo.findById(id_cita)
              .orElseThrow(()->{
                  log.warn("cita no encontrado con ID: {}", id_cita);
                  return new EntidadNoEncontradaException("cita con ID " + id_cita + " no encontrado.");
              });
        log.info("cita encontrado exitosamente con ID: {}", id_cita);
        return Optional.of(cita);
    }

    @Transactional
    @Override
    public void editCita(Long id_cita, CitaEntity nuevo_cita) {
        CitaEntity existente = citaRepo.findById(id_cita)
                .orElseThrow(() -> {
                    log.error("No se puede editar, cita no existe con ID: {}", id_cita);
                    return new EntidadNoEncontradaException("Cita con ID " + id_cita + " no encontrado para edición.");
                });

        // Proteger campos que podrían ser null
        if (nuevo_cita.getFechaCita() != null) {
            existente.setFechaCita(nuevo_cita.getFechaCita());
        }

        if (nuevo_cita.getEstadoCita() != null) {
            existente.setEstadoCita(nuevo_cita.getEstadoCita());
        }

        if (nuevo_cita.getCliente() != null) {
            existente.setCliente(nuevo_cita.getCliente());
        } else if (existente.getCliente() == null) {
            // Esto previene que el cliente quede null por error si ambos son null
            throw new IllegalArgumentException("El cliente no puede ser null al editar la cita.");
        }

        if (nuevo_cita.getObservaciones() != null) {
            existente.setObservaciones(nuevo_cita.getObservaciones());
        }

        if (nuevo_cita.getTipoCita() != null) {
            existente.setTipoCita(nuevo_cita.getTipoCita());
        }

        if (nuevo_cita.getFactura() != null) {
            existente.setFactura(nuevo_cita.getFactura());
        }

        citaRepo.save(existente);
        log.info("Cita actualizada con ID: {}", id_cita);
    }


    @Override
    public List<CitaEntity> buscarCitasPorClienteId(Long clienteId) {
        List<CitaEntity> citas = citaRepo.buscarCitasPorClienteId(clienteId);
        if (citas.isEmpty()) {
            log.warn("No se encontraron citas para el cliente con ID: {}", clienteId);
            throw new EntidadNoEncontradaException("No se encontraron citas para el cliente con ID: " + clienteId);
        }
        return citas;
    }

}
