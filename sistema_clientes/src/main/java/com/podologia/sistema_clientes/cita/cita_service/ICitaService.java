package com.podologia.sistema_clientes.cita.cita_service;

import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;

import java.util.List;
import java.util.Optional;

public interface ICitaService {
    List<CitaEntity> getCita();
   void saveCita(CitaEntity citaEntity);
   void deleteCita(Long id_cita);
   Optional<CitaEntity> findCita(long id_cita);
   void editCita(Long id_cita,CitaEntity nuevo_cita);
   List<CitaEntity> buscarCitasPorClienteId(Long clienteId);


}
