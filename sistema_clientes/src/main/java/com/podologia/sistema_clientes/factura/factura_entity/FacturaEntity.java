package com.podologia.sistema_clientes.factura.factura_entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.enume.FormatoRecibo;
import com.podologia.sistema_clientes.enume.TipoPago;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FacturaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;

    @Column(unique = true, nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String numeroRecibo;//usar un metodo para generar un numeroFactura en shared -> util, ahi crearlo, por mientras crearlo manualmente

    @ManyToOne
    @JoinColumn(name = "cliente_id",nullable = false)
    //@JsonIgnoreProperties({"facturas"})
    @JsonIgnoreProperties({"listaFactura", "listaCita"})
    private ClienteEntity clienteEntity;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    //unique = true impone una restricción de uno a uno en base de datos (¡clave!).
    @JoinColumn(name = "cita_id",nullable = false ,unique = true) // esta columna será la foreign key
    //@JsonBackReference
    private CitaEntity citaEntity;

    private LocalDateTime fechaEnmision;
    private double totalFactura;
    private TipoPago tipoPago;
    private FormatoRecibo formatoRecibo;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FacturaEntity that = (FacturaEntity) o;
        return Objects.equals(idFactura, that.idFactura) && Objects.equals(numeroRecibo, that.numeroRecibo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFactura, numeroRecibo);
    }
}
