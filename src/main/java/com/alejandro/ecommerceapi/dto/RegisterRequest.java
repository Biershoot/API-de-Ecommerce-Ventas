package com.alejandro.ecommerceapi.dto;

import com.alejandro.ecommerceapi.entity.Role;
import lombok.Data;

/**
 * Objeto de Transferencia de Datos (DTO) para solicitudes de registro de usuarios.
 * 
 * <p>Esta clase se utiliza para transferir datos de registro de usuarios desde el cliente
 * al servidor cuando se crea una nueva cuenta de usuario. Contiene toda la
 * información necesaria para crear un nuevo usuario incluyendo nombre, email,
 * contraseña y rol.</p>
 * 
 * <p>La clase utiliza la anotación {@code @Data} de Lombok para generar automáticamente
 * getters, setters, toString, equals y métodos hashCode.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Data
public class RegisterRequest {
    /**
     * Nombre completo del usuario a registrar.
     */
    private String name;
    
    /**
     * Dirección de correo electrónico para la nueva cuenta de usuario.
     * Debe ser único en todos los usuarios del sistema.
     */
    private String email;
    
    /**
     * Contraseña para la nueva cuenta de usuario.
     * Será encriptada antes de almacenar en la base de datos.
     */
    private String password;
    
    /**
     * Rol asignado al nuevo usuario (CLIENT o ADMIN).
     * Determina los permisos y niveles de acceso del usuario.
     */
    private Role role; // CLIENT o ADMIN
}
