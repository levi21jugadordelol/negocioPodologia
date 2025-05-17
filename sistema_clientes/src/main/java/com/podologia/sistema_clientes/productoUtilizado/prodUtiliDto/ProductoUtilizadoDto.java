package com.podologia.sistema_clientes.productoUtilizado.prodUtiliDto;


import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoUtilizadoDto {
    private Long idProductoUtilizado;

    // Producto: mostrar lo mínimo necesario
    private Long productoId;
    private String nombreProducto;

    private int cantidadUtilizada;
}
