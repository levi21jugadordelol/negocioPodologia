package com.podologia.sistema_clientes.servicio.servicio_dtos;

import com.podologia.sistema_clientes.detalleCita.detalle_dtos.DetalleDto;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ServicioDto {
    private Long idServicio;
    private String nombreServicio;
    private double precioServicio;
    private String descripcionServicio;
    private double duracionServicio;

    private List<DetalleDto> listaDetalle;
}
