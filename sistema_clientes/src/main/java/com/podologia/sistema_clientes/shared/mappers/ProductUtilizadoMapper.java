package com.podologia.sistema_clientes.shared.mappers;

import com.podologia.sistema_clientes.producto.producto_dtos.ProductDto;
import com.podologia.sistema_clientes.productoUtilizado.prodUtiliDto.ProductoUtilizadoDto;
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

    @Mapping(target = "productoEntity", ignore = true)
    @Mapping(target = "detalleEntity", ignore = true) // también se debe ignorar
    ProductUtilizadoEntity toProductUtiliEntity(ProductoUtilizadoDto productoUtilizadoDto);


}





/*public class ProductUtilizadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProductoUtilizado;

    @ManyToOne
    @JoinColumn(name = "detalle_id",nullable = false)
    private DetalleEntity detalleEntity;

    @ManyToOne
    @JoinColumn(name = "producto_id",nullable = false)
    private ProductoEntity productoEntity;

    private int cantidadUtilizada;

--------------------------------------------------

    public class ProductoUtilizadoDto {

    private Long idProductoUtilizado;
    // Producto: mostrar lo mínimo necesario
    private Long productoId;
    private String nombreProducto;

    private int cantidadUtilizada;
}

}*/