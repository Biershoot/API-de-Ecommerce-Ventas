package com.alejandro.ecommerceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Objeto de Transferencia de Datos (DTO) para respuestas de autenticación.
 * 
 * <p>Esta clase se utiliza para transferir resultados de autenticación desde el servidor
 * al cliente después de un inicio de sesión o registro exitoso. Contiene el
 * token JWT que el cliente utilizará para solicitudes autenticadas posteriores.</p>
 * 
 * <p>La clase utiliza anotaciones de Lombok para generar automáticamente getters,
 * setters y constructores.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    /**
     * Token JWT generado para el usuario autenticado.
     * Este token debe incluirse en el header de Authorization
     * para solicitudes posteriores de la API que requieran autenticación.
     */
    private String token;
}
