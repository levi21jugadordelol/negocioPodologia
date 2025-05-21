package com.podologia.sistema_clientes.cita.cita_service;

import com.podologia.sistema_clientes.cita.cita_dtos.CitaRequestDto;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;

import java.util.List;
import java.util.Optional;

public interface ICitaService {
    List<CitaEntity> getCita();
   void saveCita(CitaEntity citaEntity);
   DetalleEntity saveDetalle(Long idCita, DetalleEntity detalle);
   void deleteCita(Long id_cita);
   Optional<CitaEntity> findCita(long id_cita);
   void editCita(Long id_cita, CitaRequestDto citaRequestDto);
   List<CitaEntity> buscarCitasPorClienteId(Long clienteId);


}
