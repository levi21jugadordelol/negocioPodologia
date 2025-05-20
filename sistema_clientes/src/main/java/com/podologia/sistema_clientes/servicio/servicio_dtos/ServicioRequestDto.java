package com.podologia.sistema_clientes.servicio.servicio_dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioRequestDto {
    private String nombreServicio;
    private double precioServicio;
    private String descripcionServicio;
    private double duracionServicio;
}
