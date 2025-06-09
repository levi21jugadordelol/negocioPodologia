package com.podologia.sistema_clientes.shared.mappers;

import com.podologia.sistema_clientes.usuario.dto.UsuarioDto;
import com.podologia.sistema_clientes.usuario.usuario_entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // Entity → DTO (para mostrar info)
    UsuarioDto toUsuarioDto(UsuarioEntity entity);

    // DTO → Entity (para registrar)
    @Mapping(target = "idUsuario", ignore = true) // Lo genera la DB
    @Mapping(target = "status", ignore = true) // Se asignará por defecto en el servicio
    UsuarioEntity toUsuarioEntity(UsuarioDto dto);
}
