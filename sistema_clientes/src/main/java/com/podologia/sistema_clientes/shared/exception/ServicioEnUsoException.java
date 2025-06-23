package com.podologia.sistema_clientes.shared.exception;

public class ServicioEnUsoException extends RuntimeException {
    public ServicioEnUsoException(String mensaje) {
        super(mensaje);
    }
}

