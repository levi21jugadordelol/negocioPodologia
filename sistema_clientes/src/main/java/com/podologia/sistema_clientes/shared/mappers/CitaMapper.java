package com.podologia.sistema_clientes.shared.mappers;

import com.podologia.sistema_clientes.cita.cita_dtos.CitaDto;
import com.podologia.sistema_clientes.cita.cita_dtos.CitaRequestDto;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = {DetalleMapper.class, ServicioMapper.class})

public interface CitaMapper {

  @Mappings({
          @Mapping(source = "cliente.nombreCliente", target = "nombreCliente"),
          @Mapping(source = "cliente.idCliente", target = "clienteId"),
          @Mapping(source = "factura.idFactura", target = "facturaId"),
          @Mapping(source = "servicio", target = "servicioDto"), // esto es suficiente
          @Mapping(source = "listaDetalle", target = "detalles"),
          @Mapping(source = "createdAt", target = "createdAt")
  })
  CitaDto toCitaDto(CitaEntity citaEntity);





  // CitaRequestDto → CitaEntity (ingreso)
  @Mappings({
          @Mapping(target = "idCita", ignore = true),
          @Mapping(target = "cliente", ignore = true),
          @Mapping(target = "factura", ignore = true),
          @Mapping(target = "listaDetalle", source = "detalles"),
          @Mapping(target = "servicio", expression = "java(mapServicio(requestDto.getServicioId()))")

  })
    CitaEntity toCitaEntity(CitaRequestDto requestDto);
  default ServicioEntity mapServicio(Long id) {
    if (id == null) return null;
    ServicioEntity servicio = new ServicioEntity();
    servicio.setIdServicio(id);
    return servicio;
  }

  // NUEVO método para actualizar entidad existente con datos del DTO
  @Mappings({
          @Mapping(target = "idCita", ignore = true), // no tocar ID
          @Mapping(target = "cliente", ignore = true), // se setea manual
          @Mapping(target = "factura", ignore = true),
          @Mapping(target = "listaDetalle", ignore = true)
  })
  void updateCitaEntityFromDto(CitaRequestDto requestDto, @MappingTarget CitaEntity entity);


}
