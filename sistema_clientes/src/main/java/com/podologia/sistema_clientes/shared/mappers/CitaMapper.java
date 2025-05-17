package com.podologia.sistema_clientes.shared.mappers;

import com.podologia.sistema_clientes.cita.cita_dtos.CitaDto;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = {DetalleMapper.class})
public interface CitaMapper {

  @Mappings({
          @Mapping(source = "cliente.nombre", target = "nombreCliente"),
          @Mapping(source = "factura.idFactura", target = "facturaId"),
          @Mapping(source = "listaDetalle", target = "detalles")
  })
    CitaDto toCitaDto(CitaEntity citaEntity);


    @Mapping(target = "cliente", ignore = true) // se setea manualmente en el servicio
    @Mapping(target = "factura", ignore = true) // por lo general se setea luego
    CitaEntity toCitaEntity(CitaDto dto);


}
