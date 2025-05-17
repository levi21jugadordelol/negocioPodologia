package com.podologia.sistema_clientes.shared.mappers;

import com.podologia.sistema_clientes.detalleCita.detalle_dtos.DetalleDto;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

// te permite inyectar este mapper.
@Mapper(componentModel = "spring", uses = {ProductUtilizadoMapper.class})
public interface DetalleMapper {

   //Mapea campos de servicio anidados.
   //Mapea la colección listProductUtilziado → productosUtilizados usando automáticamente ProductUtilizadoMapper
    @Mappings({
            @Mapping(source = "servicio.id", target="servicioId"),
            @Mapping(source = "servicio.nombre", target="nombreServicio"),
            @Mapping(source = "listProductUtilizado", target = "productosUtilizados")
    })
    DetalleDto toDetalleDto(DetalleEntity detalleEntity);

    // Ignoramos campos que no se pueden mapear directamente
    @Mapping(target = "servicio", ignore = true)
    @Mapping(target = "listProductUtilizado", source = "productosUtilizados")
    DetalleEntity toDetalleEntity(DetalleDto dto);

}
