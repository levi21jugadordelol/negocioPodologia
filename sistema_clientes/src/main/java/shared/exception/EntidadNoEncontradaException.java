package shared.exception;


//Significa: La entidad que buscas no existe (ej. cliente con id 100).
// Uso común: En servicios como findById o deleteById.
public class EntidadNoEncontradaException extends RuntimeException {
    public EntidadNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
