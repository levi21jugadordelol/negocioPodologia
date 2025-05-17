package com.podologia.sistema_clientes.producto.producto_dtos;


import com.podologia.sistema_clientes.productoUtilizado.prodUtiliDto.ProductoUtilizadoDto;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductDto {
    private Long idProducto;
    private String nombreProducto;
    private double stock;
    private String tipoProducto;
    private Set<ProductoUtilizadoDto> listProductUtilizado = new HashSet<>();


}
