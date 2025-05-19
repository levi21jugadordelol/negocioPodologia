package com.podologia.sistema_clientes.factura.factura_dtos;

import com.podologia.sistema_clientes.enume.FormatoRecibo;
import com.podologia.sistema_clientes.enume.TipoPago;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaDto {
    private Long idFactura;
    private String numeroRecibo;

    private String nombreCliente;
    private String apellidoCliente;
    private String dniCliente;

    private Long citaId;

    private LocalDateTime fechaEnmision;

    private double totalFactura;

    private String tipoPago;
    private String formatoRecibo;
}
