package com.podologia.sistema_clientes.detalleCita.detalle_entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.productoUtilizado.productoUtilizado_entity.ProductUtilizadoEntity;
import jakarta.persistence.*;
import lombok.*;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DetalleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleCita;

    @ManyToOne
    @JoinColumn(name="cita_id",nullable = false)
    @JsonBackReference("cita-detalle")
   // @JsonBackReference
    private CitaEntity cita;

    @ManyToOne
    @JoinColumn(name = "servicio_id",nullable = false)
   // @ToString.Exclude
   // @JsonBackReference
    @JsonBackReference("servicio-detalle")
    //@JsonIgnore
    private ServicioEntity servicio;

    private double duracionTotal;
    private String motivo;

    @OneToMany(mappedBy = "detalleEntity",
            fetch = FetchType.LAZY,
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JsonManagedReference("detalle-producto")
    private Set<ProductUtilizadoEntity> listProductUtilziado = new HashSet<>();

    public void addProductUtilizado(ProductUtilizadoEntity productUtilizadoEntity){
        this.listProductUtilziado.add(productUtilizadoEntity);
        productUtilizadoEntity.setDetalleEntity(this);
    }

    public void removeProductUtilizado(ProductUtilizadoEntity producto) {
        this.listProductUtilziado.remove(producto);
        producto.setDetalleEntity(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DetalleEntity that = (DetalleEntity) o;
        return Objects.equals(idDetalleCita, that.idDetalleCita);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idDetalleCita);
    }
}
