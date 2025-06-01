package com.podologia.sistema_clientes.cita.cita_dtos;


import com.podologia.sistema_clientes.detalleCita.detalle_dtos.DetalleDto;
import com.podologia.sistema_clientes.servicio.servicio_dtos.ServicioDto;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CitaDto {
    private Long idCita;
    private String nombreCliente;

    private LocalDateTime fechaCita;
    private String estadoCita;

    private String observaciones;

    private Long facturaId;

    private List<DetalleDto> detalles;

    // ✅ Agregado:
    private ServicioDto servicioDto;

}
