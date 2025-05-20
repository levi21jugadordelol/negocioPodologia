package com.podologia.sistema_clientes.shared.mappers;

import com.podologia.sistema_clientes.producto.producto_dtos.ProductDto;
import com.podologia.sistema_clientes.productoUtilizado.prodUtiliDto.ProductoUtilizadoDto;
import com.podologia.sistema_clientes.productoUtilizado.prodUtiliDto.ProductoUtilizadoRequestDto;
import com.podologia.sistema_clientes.productoUtilizado.productoUtilizado_entity.ProductUtilizadoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductUtilizadoMapper {

    @Mappings({
            @Mapping(source = "productoEntity.idProducto", target="productoId"),
            @Mapping(source = "productoEntity.nombreProducto", target="nombreProducto")
    })
    ProductoUtilizadoDto toProductUtiliDto(ProductUtilizadoEntity productUtilizadoEntity);

    // Request DTO → Entity
    @Mapping(target = "productoEntity", ignore = true)
    @Mapping(target = "detalleEntity", ignore = true) // también se debe ignorar
    ProductUtilizadoEntity toEntity(ProductoUtilizadoRequestDto requestDto);


}

