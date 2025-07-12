package com.alejandro.ecommerceapi.repository;

import com.alejandro.ecommerceapi.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaz de repositorio para operaciones de la entidad OrderDetail.
 * 
 * <p>Esta interfaz extiende {@link JpaRepository} para proporcionar operaciones CRUD básicas
 * para la entidad OrderDetail. Los detalles de pedido típicamente se gestionan a través de su
 * entidad padre Order usando operaciones en cascada, por lo que este repositorio principalmente
 * proporciona operaciones JPA estándar.</p>
 * 
 * <p>La interfaz aprovecha Spring Data JPA para generar automáticamente
 * clases de implementación y consultas SQL basadas en nombres de métodos.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
