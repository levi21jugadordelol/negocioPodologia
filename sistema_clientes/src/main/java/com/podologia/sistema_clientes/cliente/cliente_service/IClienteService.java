package com.podologia.sistema_clientes.cliente.cliente_service;

import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteDto;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;

import java.util.List;
import java.util.Optional;

public interface IClienteService {

    List<ClienteEntity> getCliente();
    void saveCliente(ClienteEntity clienteEntity);
    void deleteCliente(Long id_cliente);
    Optional<ClienteEntity> findCliente(Long id_cliente);
    void editCliente(Long id_cliente,ClienteEntity cliente);
    Optional<ClienteEntity> buscarClienteDni(String dni_cliente);
    Optional<ClienteEntity> buscarNombreCliente(String nombre_cliente);
    Optional<ClienteEntity> obtenerClientePorCitaId(Long cita_id);

}
