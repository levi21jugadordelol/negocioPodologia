package com.podologia.sistema_clientes.servicio.servicio_service;


import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.servicio.IServicioRepo;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
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
public class ServicioServiceImpl implements IServicioService {

    private final IServicioRepo servicioRepo;
    private final ICitaRepo citaRepo;

    @Override
    public List<ServicioEntity> getServicios() {
       List<ServicioEntity> listaServicio = servicioRepo.findAll();
        log.info("el total de lista de servicios es : {}",listaServicio.size());
        return  listaServicio;
    }

    @Transactional
    @Override
    public ServicioEntity saveServicio(ServicioEntity servicio) {
        // Establecer relación bidireccional detalle-servicio
        if (servicio.getListDetalle() != null) {
            for (DetalleEntity detalle : servicio.getListDetalle()) {
                detalle.setServicio(servicio);
            }
        }

        // Guardar y devolver el servicio
        return servicioRepo.save(servicio);
    }




    @Transactional
    @Override
    public void deleteServicio(Long id_servicio) {
           if(!servicioRepo.existsById(id_servicio)){

               log.error("no existe dicho id {}", id_servicio);
               throw new EntidadNoEncontradaException("servicio con ID " + id_servicio + " no existe.");

           }else{
               servicioRepo.deleteById(id_servicio);
               log.info("el id fue eliminado correctamente: {}",id_servicio);
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
    public void editServicio(Long id_servicio, ServicioEntity servicio) {
        ServicioEntity servicioEdit = servicioRepo.findById(id_servicio)
                .orElseThrow(()->{
                    log.warn("servicio no encontrado con ID: {}", id_servicio);
                    return new EntidadNoEncontradaException("servicio con ID " + id_servicio + " no encontrado.");
                });
        servicio.setIdServicio(id_servicio);
        servicioRepo.save(servicio);
        log.info("servicio actualizado con ID: {}", id_servicio);
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
    public void validarDetalles(ServicioEntity servicio) {
        if (servicio.getListDetalle() == null || servicio.getListDetalle().isEmpty()) {
            throw new IllegalArgumentException("El servicio debe tener al menos un detalle");
        }

        for (DetalleEntity detalle : servicio.getListDetalle()) {
            if (detalle.getCita() == null || detalle.getCita().getIdCita() == null) {
                throw new IllegalArgumentException("Cada detalle debe tener una cita con ID");
            }
        }
    }



}
