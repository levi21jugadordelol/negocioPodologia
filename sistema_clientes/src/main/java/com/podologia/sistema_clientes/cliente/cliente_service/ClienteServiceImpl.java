package com.podologia.sistema_clientes.cliente.cliente_service;

import com.podologia.sistema_clientes.cliente.IClienteRepo;
import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteDto;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.shared.util.mapper.ClienteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService{

    private final IClienteRepo clienteRepo;
    private  final ClienteMapper clienteMapper;

    @Override
    public List<ClienteDto> getCliente() {
        return List.of();
    }

    @Override
    public void saveCliente(ClienteDto clienteEntity) {

    }

    @Override
    public void deleteCliente(Long id_cliente) {

    }

    @Override
    public Optional<ClienteDto> findCliente(Long id_cliente) {
        return Optional.empty();
    }

    @Override
    public void editCliente(Long id_cliente, ClienteDto cliente) {

    }

    @Override
    public Optional<ClienteDto> buscarClienteDni(String dni_cliente) {
        return Optional.empty();
    }

    @Override
    public Optional<ClienteDto> buscarNombreCliente(String nombre_cliente) {
        return Optional.empty();
    }



}
