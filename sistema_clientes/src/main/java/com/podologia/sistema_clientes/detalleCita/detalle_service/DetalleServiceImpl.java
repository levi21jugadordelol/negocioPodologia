package com.podologia.sistema_clientes.detalleCita.detalle_service;

import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.detalleCita.IDetalleRepo;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
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
public class DetalleServiceImpl implements IDetalleService{

    private final IDetalleRepo detalleRepo;
    private final ICitaRepo citaRepo;

    @Override
    public List<DetalleEntity> listaDetalle() {
        List<DetalleEntity> listaDetalles = detalleRepo.findAll();
        log.info("el total de deatlles de la cita es : {}",listaDetalles.size());
        return  listaDetalles;
    }

    @Transactional
    @Override
    public void saveDetalle(DetalleEntity detalleEntity, Long idCita) {
        CitaEntity cita = citaRepo.findById(idCita)
                .orElseThrow(() -> new EntidadNoEncontradaException("Cita no encontrada"));

        detalleEntity.setCita(cita);

        detalleRepo.save(detalleEntity);
        log.info("detalle guardado con éxito: {}", detalleEntity);
    }


    @Transactional
    @Override
    public void deleteDetalle(Long id_detalle) {
     if(!detalleRepo.existsById(id_detalle)){
         log.error("no existe dicho id {}", id_detalle);
         throw new EntidadNoEncontradaException("detalle con ID " + id_detalle + " no existe.");
     }else{
         detalleRepo.deleteById(id_detalle);
         log.info("el id fue eliminado correctamente: {}",id_detalle);
     }
    }


    @Override
    public Optional<DetalleEntity> findDetalle(Long id_detalle) {
        DetalleEntity detalle = detalleRepo.findById(id_detalle)
                .orElseThrow(() -> {
                    log.warn("Detalle no encontrado con ID: {}", id_detalle);
                    return new EntidadNoEncontradaException("Detalle con ID " + id_detalle + " no encontrado.");
                });

        log.info("Detalle encontrado exitosamente con ID: {}", id_detalle);
        return Optional.of(detalle);
    }


    @Transactional
    @Override
    public void editDetalle(Long id_detalle, DetalleEntity detalle) {
         DetalleEntity existente = detalleRepo.findById(id_detalle)
                 .orElseThrow(()->{
                     log.error("No se puede editar, detalle no existe con ID: {}", id_detalle);
                     return new EntidadNoEncontradaException("detalle con ID " + id_detalle + " no encontrado para edición.");
                 });
         detalle.setIdDetalleCita(id_detalle);
         detalleRepo.save(detalle);
        log.info("detalle actualizado con ID: {}", id_detalle);
    }

    @Override
    public List<DetalleEntity> detallesBaseServicioId(Long id_servicio) {
        List<DetalleEntity> listaCitas = detalleRepo.findByServicioId(id_servicio);
        if(listaCitas.isEmpty()){
            log.warn("No se encontraron detalles con el serivio del ID: {}", id_servicio);
            throw new EntidadNoEncontradaException("No se encontraron detalles con la cita con el ID: " + id_servicio);
        }
        return  listaCitas;
    }
}
