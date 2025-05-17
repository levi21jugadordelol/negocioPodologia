package com.podologia.sistema_clientes.detalleCita.detalle_dtos;

import com.podologia.sistema_clientes.productoUtilizado.prodUtiliDto.ProductoUtilizadoDto;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleDto {
    private Long idDetalleCita;

    private Long servicioId;
    private String nombreServicio;

    private double duracionTotal;
    private String motivo;

    private List<ProductoUtilizadoDto> productosUtilizados;
}
