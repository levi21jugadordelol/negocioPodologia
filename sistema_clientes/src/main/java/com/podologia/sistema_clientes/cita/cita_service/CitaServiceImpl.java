package com.podologia.sistema_clientes.cita.cita_service;

import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.cliente.IClienteRepo;
import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteDto;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.cliente.cliente_service.IClienteService;
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
            }else{
                citaRepo.save(citaEntity);
            }
    }

    @Transactional
    @Override
    public void deleteCita(Long id_cita) {
        if(citaRepo.existsById(id_cita)){
            citaRepo.deleteById(id_cita);
            log.info("el id fue eliminado correctamente: {}",id_cita);

        }else{
            log.error("no existe dicho id {}", id_cita);
        }


    }

    @Override
    public Optional<CitaEntity> findCita(long id_cita) {
        Optional<CitaEntity> cita = citaRepo.findById(id_cita);
        if(cita.isPresent()){
            log.info("cita encontrada con id:{}",id_cita);
        }else{
            log.warn("no se encontro cita con el id: {}",id_cita);
        }
        return cita;
    }

    @Override
    public void editCita(Long id_cita, CitaEntity nuevo_cita) {
          Optional<CitaEntity> idCita = citaRepo.findById(id_cita);
          if(idCita.isPresent()){
              nuevo_cita.setIdCita(id_cita);
              citaRepo.save(nuevo_cita);
              log.info("cita actualizada con el id : {}",id_cita);
          }else{
              log.error("Intento de editar una cita inexistente con ID: {}\", id_cita");
          }
    }

    @Override
    public List<CitaEntity> buscarCitasPorClienteId(Long clienteId) {

        return citaRepo.buscarCitasPorClienteId(clienteId);


    }
}
