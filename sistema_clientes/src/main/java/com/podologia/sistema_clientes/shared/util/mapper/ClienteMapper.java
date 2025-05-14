package com.podologia.sistema_clientes.shared.util.mapper;

import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteDto;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    private final ModelMapper modelMapper;

    public ClienteMapper() {
        this.modelMapper = new ModelMapper();

        this.modelMapper
                .typeMap(ClienteDto.class, ClienteEntity.class)
                .addMappings(mapper -> {
                    mapper.map(ClienteDto::getNombreCliente, ClienteEntity::setNombreCliente);
                    mapper.map(ClienteDto::getApellidoCliente, ClienteEntity::setApellidoCliente);
                    mapper.map(ClienteDto::getDniCliente, ClienteEntity::setDniCliente);
                    mapper.map(ClienteDto::getCellCliente, ClienteEntity::setCellCliente);
                    mapper.map(ClienteDto::getCorreoCliente, ClienteEntity::setCorreoCliente);
                });

        this.modelMapper
                .typeMap(ClienteEntity.class, ClienteDto.class)
                .addMappings(mapper -> {
                    mapper.map(ClienteEntity::getNombreCliente, ClienteDto::setNombreCliente);
                    mapper.map(ClienteEntity::getApellidoCliente, ClienteDto::setApellidoCliente);
                    mapper.map(ClienteEntity::getDniCliente, ClienteDto::setDniCliente);
                    mapper.map(ClienteEntity::getCellCliente, ClienteDto::setCellCliente);
                    mapper.map(ClienteEntity::getCorreoCliente, ClienteDto::setCorreoCliente);
                });
    }

    public ClienteEntity toEntity(ClienteDto dto) {
        return modelMapper.map(dto, ClienteEntity.class);
    }

    public ClienteDto toDto(ClienteEntity entity) {
        return modelMapper.map(entity, ClienteDto.class);
    }
}
