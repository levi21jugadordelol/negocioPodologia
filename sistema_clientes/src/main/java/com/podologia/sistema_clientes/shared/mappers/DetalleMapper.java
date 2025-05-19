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
           @Mapping(source = "servicio.idServicio", target="servicioId"),
           @Mapping(source = "servicio.nombreServicio", target="nombreServicio"),
           @Mapping(source = "listProductUtilziado", target = "productosUtilizados")
   })
   DetalleDto toDetalleDto(DetalleEntity detalleEntity);

 @Mapping(target = "servicio", ignore = true)
 @Mapping(target = "listProductUtilziado", source = "productosUtilizados")
 DetalleEntity toDetalleEntity(DetalleDto dto);
}
