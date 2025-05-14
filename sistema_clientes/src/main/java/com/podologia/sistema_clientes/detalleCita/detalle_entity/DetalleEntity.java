package com.podologia.sistema_clientes.detalleCita.detalle_entity;

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
    private CitaEntity cita;

    @ManyToOne
    @JoinColumn(name = "servicio_id",nullable = false)
    private ServicioEntity servicio;

    private double duracion_total;
    private String motivo;

    @OneToMany(mappedBy = "detalleEntity",
            fetch = FetchType.LAZY,
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private Set<ProductUtilizadoEntity> listProductUtilziado = new HashSet<>();

    public void addProductUtilizado(ProductUtilizadoEntity productUtilizadoEntity){
        this.listProductUtilziado.add(productUtilizadoEntity);
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
