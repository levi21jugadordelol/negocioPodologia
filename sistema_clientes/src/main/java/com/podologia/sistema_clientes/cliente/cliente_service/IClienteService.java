package com.podologia.sistema_clientes.cliente.cliente_service;

import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteDto;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;

import java.util.List;
import java.util.Optional;

public interface IClienteService {

    List<ClienteDto> getCliente();
    void saveCliente(ClienteDto clienteEntity);
    void deleteCliente(Long id_cliente);
    Optional<ClienteDto> findCliente(Long id_cliente);
    void editCliente(Long id_cliente,ClienteDto cliente);
    Optional<ClienteDto> buscarClienteDni(String dni_cliente);
    Optional<ClienteDto> buscarNombreCliente(String nombre_cliente);

}
