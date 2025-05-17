package com.podologia.sistema_clientes.shared.mappers;

import com.podologia.sistema_clientes.servicio.servicio_dtos.ServicioDto;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {DetalleMapper.class})
public interface ServicioMapper {


    @Mappings({
            @Mapping(source = "listDetalle", target = "listaDetalle") // entidad → dto
    })
    ServicioDto toServicioDto(ServicioEntity servicioEntity);

    @Mappings({
            @Mapping(source = "listaDetalle", target = "listDetalle", ignore = true) // por ahora se ignora si no se gestiona desde aquí
    })
    ServicioEntity toServicioEntity(ServicioDto servicioDto);

}


