package com.alejandro.ecommerceapi.repository;

import com.alejandro.ecommerceapi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz de repositorio para operaciones de la entidad Product.
 * 
 * <p>Esta interfaz extiende {@link JpaRepository} para proporcionar operaciones CRUD básicas
 * para la entidad Product. También incluye métodos de consulta personalizados para operaciones
 * específicas de productos como buscar productos por nombre con soporte de paginación.</p>
 * 
 * <p>La interfaz aprovecha Spring Data JPA para generar automáticamente
 * clases de implementación y consultas SQL basadas en nombres de métodos.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Encuentra productos por nombre con búsqueda insensible a mayúsculas y soporte de paginación.
     * 
     * <p>Este método realiza una búsqueda insensible a mayúsculas para productos cuyos nombres
     * contengan el término de búsqueda especificado. La búsqueda es parcial, lo que significa que
     * coincidirá con productos donde el nombre contenga el término de búsqueda en cualquier lugar dentro de él.</p>
     * 
     * <p>Los resultados se devuelven como una lista paginada, permitiendo un manejo eficiente
     * de grandes conjuntos de datos.</p>
     * 
     * @param name El término de búsqueda a buscar en los nombres de productos
     * @param pageable Parámetros de paginación y ordenamiento
     * @return Una Page que contiene los productos coincidentes con metadatos de paginación
     */
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

