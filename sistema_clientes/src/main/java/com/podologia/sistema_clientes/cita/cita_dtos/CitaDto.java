package com.podologia.sistema_clientes.cita.cita_dtos;


import com.podologia.sistema_clientes.detalleCita.detalle_dtos.DetalleDto;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaDto {
    private Long idCita;
    private String nombreCliente;

    private String tipoCita;
    private LocalDateTime fechaCita;
    private String estadoCita;

    private String observaciones;

    private Long facturaId;

    private List<DetalleDto> detalles;


}
