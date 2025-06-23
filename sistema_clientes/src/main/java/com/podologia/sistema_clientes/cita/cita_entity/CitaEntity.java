package com.podologia.sistema_clientes.cita.cita_entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.enume.EstadoCita;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@ToString(exclude = {"cliente", "factura", "listaDetalle"})
@Table(name = "cita_entity")
public class CitaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    //@JsonBackReference("cliente-cita") //en el lado hijo (propiedad que apunta al padre).
    private ClienteEntity cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servicio_id")
    private ServicioEntity servicio;


    private LocalDateTime fechaCita;

    @Enumerated(EnumType.STRING)
    private EstadoCita estadoCita;

    private String observaciones;

    @OneToOne(mappedBy = "citaEntity",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
   // @JsonManagedReference
    @JsonIgnore
    private FacturaEntity factura;

    //@JsonManagedReference

    @OneToMany(mappedBy = "cita",
    fetch = FetchType.LAZY,
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JsonManagedReference("cita-detalle")
    private List<DetalleEntity> listaDetalle = new ArrayList<>();

    public void addDetalle(DetalleEntity detalleEntity){
        this.listaDetalle.add(detalleEntity);
        detalleEntity.setCita(this);
    }

    public void removeDetalle(DetalleEntity detalleEntity){
        this.listaDetalle.remove(detalleEntity);
        detalleEntity.setCita(null);
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

    @PrePersist
    private void prePersist(){
        this.setCreatedAt(LocalDateTime.now());
        log.info("pre persiste {}", this.getCreatedAt().toString());
    }
}
