package com.podologia.sistema_clientes.producto.producto_dtos;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {
    private String nombreProducto;
    private double stock;
    private String tipoProducto;
}
