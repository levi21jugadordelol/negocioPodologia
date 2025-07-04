package com.podologia.sistema_clientes.cliente.cliente_service;

import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteDto;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IClienteService {

    List<ClienteEntity> getCliente();
    ByteArrayOutputStream generarExcelClientes();
    void saveCliente(ClienteEntity clienteEntity);
    void deleteCliente(Long id_cliente);
    Optional<ClienteEntity> findCliente(Long id_cliente);
    ClienteEntity editCliente(Long id_cliente,ClienteEntity cliente);
    Optional<ClienteEntity> buscarClienteDni(String dni_cliente);
    Optional<ClienteEntity> buscarNombreCliente(String nombre_cliente);
    Optional<ClienteEntity> obtenerClientePorCitaId(Long cita_id);
    List<ClienteDto> obtenerClientes();
    List<ClienteEntity> guardarClientePorDia(LocalDate fecha);
}
