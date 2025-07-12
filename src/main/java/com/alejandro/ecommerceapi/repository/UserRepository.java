package com.alejandro.ecommerceapi.repository;

import com.alejandro.ecommerceapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interfaz de repositorio para operaciones de la entidad User.
 * 
 * <p>Esta interfaz extiende {@link JpaRepository} para proporcionar operaciones CRUD básicas
 * para la entidad User. También incluye métodos de consulta personalizados para operaciones
 * específicas de usuarios como encontrar usuarios por dirección de email.</p>
 * 
 * <p>La interfaz aprovecha Spring Data JPA para generar automáticamente
 * clases de implementación y consultas SQL basadas en nombres de métodos.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Encuentra un usuario por su dirección de email.
     * 
     * <p>Este método realiza una búsqueda sensible a mayúsculas para un usuario con la
     * dirección de email especificada. Dado que las direcciones de email son únicas en el sistema,
     * este método devolverá como máximo un usuario.</p>
     * 
     * @param email La dirección de email a buscar
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no existe
     *         ningún usuario con la dirección de email dada
     */
    Optional<User> findByEmail(String email);
}