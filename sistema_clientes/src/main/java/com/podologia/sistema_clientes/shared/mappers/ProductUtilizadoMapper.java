package com.podologia.sistema_clientes.shared.mappers;

import com.podologia.sistema_clientes.producto.producto_dtos.ProductDto;
import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;
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
    @Mapping(target = "detalleEntity", ignore = true)
    @Mapping(target = "productoEntity", expression = "java(mapProductoEntity(requestDto.getProductoId()))")
    ProductUtilizadoEntity toEntity(ProductoUtilizadoRequestDto requestDto);

    // método de ayuda
    default ProductoEntity mapProductoEntity(Long productoId) {
        if (productoId == null) return null;
        ProductoEntity p = new ProductoEntity();
        p.setIdProducto(productoId);
        return p;
    }

}

