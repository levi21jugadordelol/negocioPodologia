package com.podologia.sistema_clientes.shared.mappers;

import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteDto;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CitaMapper.class})
public interface ClienteMapper {


    // Entity a DTO
    ClienteDto toClienteDto(ClienteEntity clienteEntity);

    // DTO a Entity (por ejemplo, al crear o editar un cliente)
    ClienteEntity toClienteEntity(ClienteDto clienteDto);

}

