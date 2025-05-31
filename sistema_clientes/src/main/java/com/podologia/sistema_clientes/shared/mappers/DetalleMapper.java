package com.podologia.sistema_clientes.shared.mappers;

import com.podologia.sistema_clientes.detalleCita.detalle_dtos.DetalleDto;
import com.podologia.sistema_clientes.detalleCita.detalle_dtos.DetalleRequestDto;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
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
           @Mapping(source = "listProductUtilziado", target = "productosUtilizados"),
           @Mapping(source = "duracionTotal", target = "duracionTotal") // ✅ ESTA ES LA LÍNEA CLAVE QUE FALTA
   })
   DetalleDto toDetalleDto(DetalleEntity detalleEntity);

    // RequestDto → Entity para crear
    @Mappings({
            @Mapping(target = "idDetalleCita", ignore = true),
            @Mapping(target = "cita", ignore = true),
            @Mapping(target = "servicio", expression = "java(mapServicio(requestDto.getServicioId()))"),
            @Mapping(target = "listProductUtilziado", source = "productosUtilizados"),
            @Mapping(target = "duracionTotal", source = "duracionTotal") // ✅ ESTA LÍNEA FALTABA
    })
    DetalleEntity toDetalleEntity(DetalleRequestDto requestDto);

    default ServicioEntity mapServicio(Long servicioId) {
        if (servicioId == null) return null;
        ServicioEntity servicio = new ServicioEntity();
        servicio.setIdServicio(servicioId);
        return servicio;
    }
}
