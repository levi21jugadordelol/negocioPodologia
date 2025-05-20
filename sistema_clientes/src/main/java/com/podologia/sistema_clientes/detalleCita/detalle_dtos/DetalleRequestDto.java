package com.podologia.sistema_clientes.detalleCita.detalle_dtos;


import com.podologia.sistema_clientes.productoUtilizado.prodUtiliDto.ProductoUtilizadoRequestDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleRequestDto {
    private Long servicioId;
    private double duracionTotal;
    private String motivo;
    private List<ProductoUtilizadoRequestDto> productosUtilizados;
}
