package com.podologia.sistema_clientes.shared.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@ControllerAdvice es una anotación de Spring que indica que una clase es un manejador global de errores
//(o también puede manejar lógica común como configuración de respuestas o validaciones).
//Centraliza el manejo de errores de todo el sistema.
//Permite retornar respuestas JSON estándar (timestamp, mensaje, estado).
//Mejora la experiencia del frontend, que sabrá qué pasó y por qué.
//Lo usas en sistemas REST modernos donde la API debe devolver información clara
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*¿Qué hace @ExceptionHandler?
    Dentro de la clase con @ControllerAdvice,
    puedes definir métodos con @ExceptionHandler(MiExcepcion.class)
    para capturar errores específicos.

    RuntimeException es una excepción no verificada
    (unchecked), es decir, no necesitas declarar
    throws ni hacer try-catch obligatoriamente.

    */

  /*  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body("Media type no soportado. Asegúrate de usar Content-Type: application/json");
    } */


    @ExceptionHandler(EntidadNoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleEntidadNoEncontrada(EntidadNoEncontradaException ex) {
        log.warn("Entidad no encontrada: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Validación personalizada fallida
    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<Map<String, Object>> handleValidacion(ValidacionException ex) {
        log.warn("Error de validación: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Operación inválida
    @ExceptionHandler(OperacionInvalidaException.class)
    public ResponseEntity<Map<String, Object>> handleOperacionInvalida(OperacionInvalidaException ex) {
        log.warn("Operación inválida: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Acceso denegado
    @ExceptionHandler(AccesoDenegadoException.class)
    public ResponseEntity<Map<String, Object>> handleAccesoDenegado(AccesoDenegadoException ex) {
        log.warn("Acceso denegado: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    // Validación con @Valid fallida (de Spring)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidacionSpring(MethodArgumentNotValidException ex) {
        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());

        log.warn("Errores de validación con @Valid: {}", errores);

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("message", "Errores de validación");
        body.put("details", errores);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Excepciones generales no controladas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleExcepcionGeneral(Exception ex) {
        log.error("Error interno no controlado", ex);
        return buildResponse("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Construcción común de respuestas de error
    private ResponseEntity<Map<String, Object>> buildResponse(String mensaje, HttpStatus status) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", mensaje);
        return new ResponseEntity<>(error, status);
    }
}
