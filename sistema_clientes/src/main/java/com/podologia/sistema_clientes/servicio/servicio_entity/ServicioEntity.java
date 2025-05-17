package com.podologia.sistema_clientes.servicio.servicio_entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
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
public class ServicioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;
    private String nombreServicio;
    private double precioServicio;
    private String descripcionServicio;
    private double duracionServicio;

    @OneToMany(mappedBy = "servicio",
            fetch = FetchType.LAZY,
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private Set<DetalleEntity> listDetalle = new HashSet<>();

    public void addDetalle(DetalleEntity detalleEntity){
        this.listDetalle.add(detalleEntity);
      detalleEntity.setServicio(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ServicioEntity that = (ServicioEntity) o;
        return Objects.equals(idServicio, that.idServicio);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idServicio);
    }
}
