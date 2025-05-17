package com.podologia.sistema_clientes.cita.cita_entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.enume.EstadoCita;
import com.podologia.sistema_clientes.enume.TipoCita;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"cliente", "factura", "listaDetalle"})
public class CitaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCita;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private ClienteEntity cliente;

    @Enumerated(EnumType.STRING)
    private TipoCita tipoCita;

    private LocalDateTime fechaCita;

    @Enumerated(EnumType.STRING)
    private EstadoCita estadoCita;

    private String observaciones;

    @OneToOne(mappedBy = "citaEntity",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private FacturaEntity factura;

    @OneToMany(mappedBy = "cita",
    fetch = FetchType.LAZY,
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private Set<DetalleEntity> listaDetalle = new HashSet<>();

    public void addDetalle(DetalleEntity detalleEntity){
        this.listaDetalle.add(detalleEntity);
        detalleEntity.setCita(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CitaEntity that = (CitaEntity) o;
        return Objects.equals(idCita, that.idCita);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idCita);
    }
}
