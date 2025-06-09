package com.podologia.sistema_clientes.shared.util;

import org.springframework.stereotype.Component;

@Component
public class secretKey {
    private  final String SECRET_KEY = "gj43jng9";
    public String getSecret() {
        // Podrías agregar controles aquí en un futuro si es necesario
        return SECRET_KEY;
    }
}
