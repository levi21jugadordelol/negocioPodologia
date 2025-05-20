package com.podologia.sistema_clientes.factura.factura_dtos;

import com.podologia.sistema_clientes.enume.FormatoRecibo;
import com.podologia.sistema_clientes.enume.TipoPago;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaRequestDto {

    private Long clienteId;
    private Long citaId;

    private double totalFactura;

    private TipoPago tipoPago;
    private FormatoRecibo formatoRecibo;
}
