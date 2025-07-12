package com.alejandro.ecommerceapi.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

/**
 * Manejador global de excepciones para la API.
 * 
 * <p>Esta clase captura excepciones no manejadas en toda la aplicación y las
 * convierte en respuestas HTTP apropiadas con formato consistente. Proporciona
 * un manejo centralizado de errores para mejorar la experiencia del cliente.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones RuntimeException genéricas.
     * 
     * @param e La excepción RuntimeException capturada
     * @return ResponseEntity con detalles del error
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntime(RuntimeException e) {
        return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones NoSuchElementException (recurso no encontrado).
     * 
     * @param e La excepción NoSuchElementException capturada
     * @return ResponseEntity con detalles del error
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(NoSuchElementException e) {
        return buildResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja excepciones de validación de argumentos de método.
     * 
     * @param e La excepción MethodArgumentNotValidException capturada
     * @return ResponseEntity con detalles del error de validación
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Error de validación");
        return buildResponse(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Construye una respuesta de error consistente.
     * 
     * @param message El mensaje de error
     * @param status El código de estado HTTP
     * @return ResponseEntity con la respuesta de error estructurada
     */
    private ResponseEntity<ApiErrorResponse> buildResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(
                new ApiErrorResponse(message, status, LocalDateTime.now()),
                status
        );
    }
}

