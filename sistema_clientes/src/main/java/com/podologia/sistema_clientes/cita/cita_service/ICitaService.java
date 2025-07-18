package com.podologia.sistema_clientes.cita.cita_service;

import com.podologia.sistema_clientes.cita.cita_dtos.CitaDto;
import com.podologia.sistema_clientes.cita.cita_dtos.CitaRequestDto;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.enume.EstadoCita;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICitaService {
    List<CitaEntity> getCita();
   CitaEntity saveCita(CitaEntity citaEntity);
   DetalleEntity saveDetalle(Long idCita, DetalleEntity detalle);
   void deleteCita(Long id_cita);
   Optional<CitaEntity> findCita(long id_cita);
   void editCita(Long id_cita, CitaRequestDto citaRequestDto);
   List<CitaEntity> buscarCitasPorClienteId(Long clienteId);
   List<CitaDto> getListPendiente(EstadoCita estadoCita);
   List<CitaDto> buscarCitaFiltradaDni(EstadoCita estadoCita, String dni);
   List<CitaDto> buscarCitaFiltradaNombre(EstadoCita estadoCita, String nombre);
   List<CitaDto> filtrarCitas(EstadoCita estado, String dni, String nombre);
   List<CitaEntity> guardarCitaPorDia(LocalDate fecha);
    Optional<ServicioEntity> obtenerServicioPorIdCita(Long idCita);

}
