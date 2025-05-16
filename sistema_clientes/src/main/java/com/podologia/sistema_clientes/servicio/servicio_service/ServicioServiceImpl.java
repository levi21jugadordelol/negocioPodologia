package com.podologia.sistema_clientes.servicio.servicio_service;


import com.podologia.sistema_clientes.servicio.IServicioRepo;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
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

    @Override
    public List<ServicioEntity> getServicios() {
       List<ServicioEntity> listaServicio = servicioRepo.findAll();
        log.info("el total de lista de servicios es : {}",listaServicio.size());
        return  listaServicio;
    }

    @Transactional
    @Override
    public void saveServicio(ServicioEntity servicio) {
           if(servicio == null){
               log.warn("no puede haber ser nullo el objeto servicio");
           }else{
               servicioRepo.save(servicio);
           }
    }

    @Transactional
    @Override
    public void deleteServicio(Long id_servicio) {
           if(servicioRepo.existsById(id_servicio)){
               servicioRepo.deleteById(id_servicio);
               log.info("el id fue eliminado correctamente: {}",id_servicio);
           }else{
               log.error("no existe dicho id {}", id_servicio);
           }
    }

    @Override
    public Optional<ServicioEntity> findServicio(Long id_servicio) {
        Optional<ServicioEntity> servicioId = servicioRepo.findById(id_servicio);
        if(servicioId.isPresent()){
            log.info("servicio encontrada con id:{}",id_servicio);
        }else{
            log.warn("no se encontro el id {}",id_servicio);
        }
        return servicioId;
    }

    @Override
    public void editServicio(Long id_servicio, ServicioEntity servicio) {
        Optional<ServicioEntity> servicioId = servicioRepo.findById(id_servicio);
        if(servicioId.isPresent()){
            servicio.setIdServicio(id_servicio);
            servicioRepo.save(servicio);
            log.info("servicio actualizada con el id :{}",id_servicio);
        }else{
            log.info("service con el id no hallada :{}",id_servicio);
        }
    }

    @Override
    public Optional<ServicioEntity> buscarNombre(String nombre_service) {
        return Optional.empty();
    }
}
