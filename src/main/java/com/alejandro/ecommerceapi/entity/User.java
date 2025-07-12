package com.alejandro.ecommerceapi.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Clase entidad que representa un usuario en el sistema de e-commerce.
 * 
 * <p>Esta clase se mapea a la tabla "users" en la base de datos y contiene
 * toda la información necesaria para la autenticación y autorización de usuarios.
 * Los usuarios pueden tener diferentes roles (ADMIN o CLIENT) que determinan sus
 * niveles de acceso dentro del sistema.</p>
 * 
 * <p>La clase utiliza anotaciones de Lombok para generar automáticamente getters,
 * setters, constructores y otro código repetitivo.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * Identificador único para el usuario.
     * Auto-generado usando estrategia de identidad de base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre completo del usuario.
     */
    private String name;

    /**
     * Dirección de correo electrónico del usuario.
     * Debe ser único en todos los usuarios del sistema.
     */
    @Column(unique = true)
    private String email;

    /**
     * Contraseña encriptada para la autenticación del usuario.
     * Debe ser hasheada antes de almacenar en la base de datos.
     */
    private String password;

    /**
     * Rol asignado al usuario (ADMIN o CLIENT).
     * Determina los permisos y niveles de acceso del usuario.
     */
    @Enumerated(EnumType.STRING)
    private Role role;
}
