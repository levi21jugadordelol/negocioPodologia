package com.podologia.sistema_clientes.shared.exception;

public class OperacionFallidaException extends RuntimeException {
    public OperacionFallidaException(String mensaje) {
        super(mensaje);
    }
}
