package productoUtilizado.productoUtilizado_entity;


import detalleCita.detalle_entity.DetalleEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import producto.producto_entity.ProductoEntity;

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
    private DetalleEntity detalleEntity;
    private ProductoEntity productoEntity;
    private double cantidadUtilizada;
}
