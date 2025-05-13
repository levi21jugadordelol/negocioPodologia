package shared.exception;

//Significa: El usuario no tiene permisos para ejecutar la acción.
//Uso común: Control de roles o seguridad.
public class AccesoDenegadoException extends RuntimeException {
    public AccesoDenegadoException(String mensaje) {
        super(mensaje);
    }
}
