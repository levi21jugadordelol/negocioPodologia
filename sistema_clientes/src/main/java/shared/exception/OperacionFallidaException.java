package shared.exception;

public class OperacionFallidaException extends RuntimeException {
    public OperacionFallidaException(String mensaje) {
        super(mensaje);
    }
}
