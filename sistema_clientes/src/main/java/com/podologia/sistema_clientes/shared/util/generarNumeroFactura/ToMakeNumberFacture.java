package com.podologia.sistema_clientes.shared.util.generarNumeroFactura;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ToMakeNumberFacture {
    public static String generarCodigoRecibo(LocalDate fecha, long contadorMesActual) {
        String anioMes = fecha.format(DateTimeFormatter.ofPattern("yyyyMM"));
        String secuencia = String.format("%04d", contadorMesActual + 1); // Empieza desde 0001
        return String.format("RC-%s-%s", anioMes, secuencia); // Ej: RC-202505-0001
    }
}
