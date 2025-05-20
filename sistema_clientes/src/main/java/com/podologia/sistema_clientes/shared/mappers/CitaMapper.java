package com.podologia.sistema_clientes.shared.mappers;

import com.podologia.sistema_clientes.cita.cita_dtos.CitaDto;
import com.podologia.sistema_clientes.cita.cita_dtos.CitaRequestDto;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = {DetalleMapper.class})
public interface CitaMapper {

  @Mappings({
          @Mapping(source = "cliente.nombreCliente", target = "nombreCliente"),
          @Mapping(source = "factura.idFactura", target = "facturaId"),
          @Mapping(source = "listaDetalle", target = "detalles")
  })
    CitaDto toCitaDto(CitaEntity citaEntity);



  // CitaRequestDto → CitaEntity (ingreso)
  @Mappings({
          @Mapping(target = "idCita", ignore = true), // lo genera la base de datos
          @Mapping(target = "cliente", ignore = true), // se setea manualmente en el servicio
          @Mapping(target = "factura", ignore = true),
          @Mapping(target = "listaDetalle", source = "detalles")
  })
    CitaEntity toCitaEntity(CitaRequestDto requestDto);



}
