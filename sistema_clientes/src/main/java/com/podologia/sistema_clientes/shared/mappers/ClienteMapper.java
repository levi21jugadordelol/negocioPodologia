package com.podologia.sistema_clientes.shared.mappers;

import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteDto;
import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteRequestDto;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CitaMapper.class})
public interface ClienteMapper {


    // Entity → DTO (para mostrar datos al cliente)
    ClienteDto toClienteDto(ClienteEntity clienteEntity);


    // Request DTO → Entity (para crear o actualizar desde petición)
    ClienteEntity toClienteEntity(ClienteRequestDto requestDto);

}

