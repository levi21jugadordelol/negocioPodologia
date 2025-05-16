package com.podologia.sistema_clientes.detalleCita.detalle_service;

import com.podologia.sistema_clientes.detalleCita.IDetalleRepo;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
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

    @Override
    public List<DetalleEntity> listaDetalle() {
        List<DetalleEntity> listaDetalles = detalleRepo.findAll();
        log.info("el total de deatlles de la cita es : {}",listaDetalles.size());
        return  listaDetalles;
    }

    @Transactional
    @Override
    public void saveDetalle(DetalleEntity detalleEntity) {
        if(detalleEntity == null){
            log.warn("no puede haber ser nullo el objeto detalle");
        }else{
            detalleRepo.save(detalleEntity);
        }

    }

    @Transactional
    @Override
    public void deleteDetalle(Long id_detalle) {
     if(detalleRepo.existsById(id_detalle)){
         detalleRepo.deleteById(id_detalle);
         log.info("el id fue eliminado correctamente: {}",id_detalle);
     }else{
         log.error("no existe dicho id {}", id_detalle);
     }


    }

    @Override
    public Optional<DetalleEntity> findDetalle(Long id_detalle) {
        Optional<DetalleEntity> detalleId = detalleRepo.findById(id_detalle);
        if(detalleId.isPresent()){
            log.info("detalle encontrada con id:{}",id_detalle);
        }else{
            log.warn("no se encontro el id {}",id_detalle);
        }
        return detalleId;
    }

    @Override
    public void editDetalle(Long id_detalle, DetalleEntity detalleEntity) {
            Optional<DetalleEntity> detalleId = detalleRepo.findById(id_detalle);
            if(detalleId.isPresent()){
                detalleEntity.setIdDetalleCita(id_detalle);
                detalleRepo.save(detalleEntity);
                log.info("detalle cita actualizada con el id :{}",id_detalle);
            }else{
                log.error("Intento de editar una cita inexistente con ID: {}\", id_detalle");
            }
    }

    @Override
    public List<DetalleEntity> detallesBaseServicioId(Long id_servicio) {
        return detalleRepo.findByServicioId(id_servicio);
    }
}
