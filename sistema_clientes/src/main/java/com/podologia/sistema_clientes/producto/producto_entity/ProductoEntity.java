package com.podologia.sistema_clientes.producto.producto_entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.podologia.sistema_clientes.productoUtilizado.productoUtilizado_entity.ProductUtilizadoEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    private String nombreProducto;
    private double stock;
    private String tipoProducto;

    @OneToMany(mappedBy = "productoEntity",
            fetch = FetchType.LAZY,
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    //@JsonManagedReference("producto-productoutilizado")
    @JsonIgnore
    private Set<ProductUtilizadoEntity> listProductUtilizado = new HashSet<>();

    public void addProductUtilizado(ProductUtilizadoEntity productUtilizadoEntity){
        this.listProductUtilizado.add(productUtilizadoEntity);
        productUtilizadoEntity.setProductoEntity(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductoEntity that = (ProductoEntity) o;
        return Objects.equals(idProducto, that.idProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idProducto);
    }
}
