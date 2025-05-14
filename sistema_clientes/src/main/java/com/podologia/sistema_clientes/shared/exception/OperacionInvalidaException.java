package com.podologia.sistema_clientes.shared.exception;

// Significa: El usuario intenta una acción no permitida o lógica errónea.
//Uso común: Intentar pagar una factura ya pagada, o eliminar algo usado por otra entidad.
public class OperacionInvalidaException extends RuntimeException {
    public OperacionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
