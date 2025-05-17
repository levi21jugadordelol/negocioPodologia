package com.podologia.sistema_clientes.shared.mappers;

import com.podologia.sistema_clientes.producto.producto_dtos.ProductDto;
import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {ProductUtilizadoMapper.class})
public interface ProductMapper {


    ProductDto toProductDto(ProductoEntity productoEntity);


    ProductoEntity toProductEntity(ProductDto productDto);

}

