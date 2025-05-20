package com.podologia.sistema_clientes.cliente.cliente_dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequestDto {
    private String nombreCliente;
    private String apellidoCliente;
    private String dniCliente;
    private String cellCliente;
    private String correoCliente;
}
