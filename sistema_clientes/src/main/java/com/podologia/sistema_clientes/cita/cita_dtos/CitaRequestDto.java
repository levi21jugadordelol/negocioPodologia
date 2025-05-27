package com.podologia.sistema_clientes.cita.cita_dtos;


import com.podologia.sistema_clientes.detalleCita.detalle_dtos.DetalleDto;
import com.podologia.sistema_clientes.detalleCita.detalle_dtos.DetalleRequestDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CitaRequestDto {
    private Long clienteId;
    private String tipoCita;
    private LocalDateTime fechaCita;
    private String estadoCita;
    private String observaciones;
    private List<DetalleRequestDto> detalles;
}
