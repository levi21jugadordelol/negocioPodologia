package com.podologia.sistema_clientes.shared.mappers;

import com.podologia.sistema_clientes.servicio.servicio_dtos.ServicioDto;
import com.podologia.sistema_clientes.servicio.servicio_dtos.ServicioRequestDto;
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

    // DTO request → Entidad (creación o actualización)
    @Mapping(target = "idServicio", ignore = true) // id lo maneja BD o se setea aparte
    @Mapping(target = "listDetalle", ignore = true) // se maneja fuera del request DTO
    ServicioEntity toServicioEntity(ServicioRequestDto requestDto);

}


