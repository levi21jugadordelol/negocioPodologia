package shared.exception;

//Significa: Algo inesperado ocurrió, como error en base de datos o servicio externo.
// Uso común: Para capturar errores generales o caídas de conexión.
public class ErrorInternoException extends RuntimeException{
    public ErrorInternoException(String mensaje) {
        super(mensaje);
    }
}
