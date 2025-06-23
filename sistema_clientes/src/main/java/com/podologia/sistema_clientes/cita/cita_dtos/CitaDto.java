package com.podologia.sistema_clientes.cita.cita_dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.podologia.sistema_clientes.detalleCita.detalle_dtos.DetalleDto;
import com.podologia.sistema_clientes.servicio.servicio_dtos.ServicioDto;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CitaDto {
    @JsonProperty("id") // 👈 así se ajusta al frontend
    private Long idCita;

    private LocalDateTime createdAt;

    private String nombreCliente;

    private Long clienteId;
   // private Long servicioId;

    private LocalDateTime fechaCita;
    private String estadoCita;

    private String observaciones;

    private Long facturaId;

    @Builder.Default // ⚠️ Nunca null
    private List<DetalleDto> detalles = new ArrayList<>();

    @JsonProperty("servicio") // 👈 así se ajusta a lo que tu frontend elimina
    private ServicioDto servicioDto;
}
