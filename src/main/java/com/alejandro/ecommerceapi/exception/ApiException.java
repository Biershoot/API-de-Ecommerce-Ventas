package com.alejandro.ecommerceapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Excepción personalizada para manejar errores específicos de la API.
 * 
 * <p>Esta clase proporciona una excepción centralizada para manejar errores
 * que ocurren durante el procesamiento de solicitudes en la API. Puede ser
 * utilizada para lanzar errores con mensajes personalizados que pueden ser
 * capturados por manejadores de excepciones globales.</p>
 * 
 * <p>La excepción extiende RuntimeException, lo que significa que no necesita
 * ser declarada en las firmas de métodos.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
public class ApiException extends RuntimeException {

    /**
     * Construye una nueva ApiException con el mensaje especificado.
     * 
     * @param message El mensaje de error que describe la excepción
     */
    public ApiException(String message) {
        super(message);
    }

    /**
     * Construye una nueva ApiException con el mensaje y causa especificados.
     * 
     * @param message El mensaje de error que describe la excepción
     * @param cause La causa original de la excepción
     */
    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * DTO para respuestas de error de la API.
 * 
 * <p>Esta clase se utiliza para estructurar las respuestas de error
 * que se envían al cliente cuando ocurre una excepción.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Data
@AllArgsConstructor
class ApiErrorResponse {
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;
}

