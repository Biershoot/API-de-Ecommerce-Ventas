package com.alejandro.ecommerceapi.dto;

import lombok.Data;

/**
 * Objeto de Transferencia de Datos (DTO) para solicitudes de inicio de sesión.
 * 
 * <p>Esta clase se utiliza para transferir credenciales de inicio de sesión desde el cliente
 * al servidor durante el proceso de autenticación. Contiene los campos de email
 * y contraseña requeridos para la autenticación de usuarios.</p>
 * 
 * <p>La clase utiliza la anotación {@code @Data} de Lombok para generar automáticamente
 * getters, setters, toString, equals y métodos hashCode.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Data
public class LoginRequest {
    /**
     * Dirección de correo electrónico del usuario que intenta iniciar sesión.
     * Debe coincidir con el email de un usuario registrado en el sistema.
     */
    private String email;
    
    /**
     * Contraseña de la cuenta de usuario.
     * Será validada contra la contraseña encriptada almacenada.
     */
    private String password;
}
