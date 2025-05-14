package com.podologia.sistema_clientes.productoUtilizado.productoUtilizado_entity;


import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import jakarta.persistence.*;
import lombok.*;
import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductUtilizadoEntity {
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
}
