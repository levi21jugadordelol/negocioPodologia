package com.podologia.sistema_clientes.productoUtilizado.prodUtiliDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoUtilizadoRequestDto {
    private Long productoId;
    private int cantidadUtilizada;
}
