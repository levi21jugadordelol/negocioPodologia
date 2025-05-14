package com.podologia.sistema_clientes.cliente.cliente_entity;

import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String dniCliente;
    private String cellCliente;
    private String correoCliente;

    @OneToMany(mappedBy = "cliente",
    fetch = FetchType.LAZY,
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private Set<CitaEntity> listaCita = new HashSet<>();

    public void addCita(CitaEntity citaEntity){
        this.listaCita.add(citaEntity);
       citaEntity.setCliente(this);
    }

    @OneToMany(mappedBy = "clienteEntity",
    fetch = FetchType.LAZY,
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private Set<FacturaEntity> listaFactura = new HashSet<>();

    public void addFactura(FacturaEntity facturaEntity){
        this.listaFactura.add(facturaEntity);
        facturaEntity.setClienteEntity(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClienteEntity that = (ClienteEntity) o;
        return Objects.equals(idCliente, that.idCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idCliente);
    }
}
