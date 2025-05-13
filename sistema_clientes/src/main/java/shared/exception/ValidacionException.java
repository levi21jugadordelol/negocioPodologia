package shared.exception;

// Significa: Los datos enviados por el usuario no son válidos (faltan campos, están mal).
// Uso común: Al crear o actualizar entidades.
public class ValidacionException extends RuntimeException {
    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}
