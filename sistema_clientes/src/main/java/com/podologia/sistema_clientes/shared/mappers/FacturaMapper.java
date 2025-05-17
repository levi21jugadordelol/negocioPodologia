package com.podologia.sistema_clientes.shared.mappers;

import com.podologia.sistema_clientes.factura.factura_dtos.FacturaDto;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface FacturaMapper {

    @Mappings({
            @Mapping(source = "clienteEntity.nombreCliente", target="nombreCliente"),
            @Mapping(source = "clienteEntity.apellidoCliente", target = "apellidoCliente"),
            @Mapping(source = "clienteEntity.dniCliente", target =  "dniCliente"),
            @Mapping(source = "citaEntity.idCita",target ="citaId"),
            @Mapping(source = "tipoPago", target = "tipoPago"),
            @Mapping(source = "formatoRecibo", target = "formatoRecibo")
    })
    FacturaDto toFacturaDto(FacturaEntity facturaEntity);

    @InheritInverseConfiguration
    /*La anotación @InheritInverseConfiguration en MapStruct se usa para invertir automáticamente un mapeo que ya definiste,
     sin tener que repetir todas las reglas @Mapping una por una. Es útil cuando el mapeo de DTO a entidad es básicamente
     la inversión exacta del mapeo de entidad a DTO.*/
    @Mappings({
            @Mapping(target = "clienteEntity", ignore = true),
            @Mapping(target = "citaEntity", ignore = true)
    })
    FacturaEntity toFacturaEntity(FacturaDto facturaDto);
}
