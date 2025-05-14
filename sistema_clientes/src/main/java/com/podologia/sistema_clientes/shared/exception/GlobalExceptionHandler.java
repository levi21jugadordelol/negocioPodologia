package com.podologia.sistema_clientes.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

//@ControllerAdvice es una anotación de Spring que indica que una clase es un manejador global de errores
//(o también puede manejar lógica común como configuración de respuestas o validaciones).
//Centraliza el manejo de errores de todo el sistema.
//Permite retornar respuestas JSON estándar (timestamp, mensaje, estado).
//Mejora la experiencia del frontend, que sabrá qué pasó y por qué.
//Lo usas en sistemas REST modernos donde la API debe devolver información clara
@ControllerAdvice
public class GlobalExceptionHandler {

    /*¿Qué hace @ExceptionHandler?
    Dentro de la clase con @ControllerAdvice,
    puedes definir métodos con @ExceptionHandler(MiExcepcion.class)
    para capturar errores específicos.

    RuntimeException es una excepción no verificada
    (unchecked), es decir, no necesitas declarar
    throws ni hacer try-catch obligatoriamente.

    */


    // Para Entidad no encontrada
    @ExceptionHandler(EntidadNoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleEntidadNoEncontrada(EntidadNoEncontradaException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Para errores de validación
    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<Map<String, Object>> handleValidacion(ValidacionException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Para operación inválida
    @ExceptionHandler(OperacionInvalidaException.class)
    public ResponseEntity<Map<String, Object>> handleOperacionInvalida(OperacionInvalidaException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Acceso denegado
    @ExceptionHandler(AccesoDenegadoException.class)
    public ResponseEntity<Map<String, Object>> handleAccesoDenegado(AccesoDenegadoException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    // Error interno o inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleExcepcionGeneral(Exception ex) {
        return buildResponse("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Método privado para dar formato JSON común
    private ResponseEntity<Map<String, Object>> buildResponse(String mensaje, HttpStatus status) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", mensaje);
        return new ResponseEntity<>(error, status);
    }
}
