package com.podologia.sistema_clientes.cliente.cliente_dtos;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {
    private String nombreCliente;
    private String apellidoCliente;
    private String dniCliente;
    private String cellCliente;
    private String correoCliente;
}
