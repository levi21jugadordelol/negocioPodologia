package com.podologia.sistema_clientes.servicio.servicio_service;


import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.detalleCita.IDetalleRepo;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.servicio.IServicioRepo;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;
import com.podologia.sistema_clientes.shared.exception.ServicioEnUsoException;
import com.podologia.sistema_clientes.shared.metodoValidaciones.ValidacionServicio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServicioServiceImpl implements IServicioService {

    private final IServicioRepo servicioRepo;
    private final ICitaRepo citaRepo;
    private final ValidacionServicio validacionServicio;
    private final IDetalleRepo detalleRepo;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.servicio.servicio_service.ServicioServiceImpl.class);

    @Override
    public List<ServicioEntity> getServicios() {
      return  validacionServicio.validateAndReturnServicios();
    }

    @Transactional
    @Override
    public ServicioEntity saveServicio(ServicioEntity servicio) {

        validacionServicio.validateServicioToSave(servicio);

        // Guardar y devolver el servicio
        return servicioRepo.save(servicio);
    }



    @Transactional
    @Override
    public void deleteServicio(Long idServicio) {
        ServicioEntity servicio = servicioRepo.findById(idServicio)
                .orElseThrow(() -> new EntidadNoEncontradaException("Servicio con ID " + idServicio + " no existe."));

        try {
            servicioRepo.delete(servicio);
            log.info("✅ Servicio con ID {} eliminado correctamente", idServicio);
        } catch (DataIntegrityViolationException ex) {
            log.warn("⛔ No se puede eliminar el servicio con ID {} porque está en uso en otra entidad", idServicio);
            throw new ServicioEnUsoException("Este servicio no se puede eliminar porque está en uso en citas.");
        }
    }





    @Override
    public Optional<ServicioEntity> findServicio(Long id_servicio) {
             ServicioEntity servicio = servicioRepo.findById(id_servicio)
                     .orElseThrow(()->{
                         log.warn("servicio no encontrado con ID: {}", id_servicio);
                         return new EntidadNoEncontradaException("servicio con ID " + id_servicio + " no encontrado.");
                     });
        log.info("servicio encontrado exitosamente con ID: {}", id_servicio);
        return Optional.of(servicio);
    }

    @Transactional
    @Override
    public ServicioEntity editServicio(Long id_servicio, ServicioEntity servicioNuevo) {
       ServicioEntity servicioEditado = validacionServicio.validateParametersToEditService(id_servicio,servicioNuevo);

       servicioRepo.save(servicioEditado);

        log.info("Servicio actualizado correctamente con ID: {}", id_servicio);

        return servicioEditado;
    }

    @Override
    public Optional<ServicioEntity> buscarNombre(String nombre_service) {
         ServicioEntity servicio = servicioRepo.findServicioByNombre(nombre_service)
                 .orElseThrow(()->{
                     log.warn("servicio no encontrado con el nombre: {}", nombre_service);
                     return new EntidadNoEncontradaException("servicio con nombre " + nombre_service + " no encontrado.");
                 });
        log.info("servicio encontrado exitosamente con el nombre: {}", nombre_service);
        return  Optional.of(servicio);
    }

    @Override
    public Optional<ServicioEntity> obtenerServicioPorIdCita(Long idCita) {
        ServicioEntity servicio = detalleRepo.findUnicoServicioByCitaId(idCita)
                .orElseThrow(() -> {
                    log.warn("No se encontró servicio para la cita con ID: {}", idCita);
                    return new EntidadNoEncontradaException("No se encontró servicio para la cita con ID " + idCita);
                });

        log.info("Servicio encontrado para la cita con ID: {}", idCita);
        return Optional.of(servicio);
    }


}
