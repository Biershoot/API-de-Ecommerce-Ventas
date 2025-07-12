package com.alejandro.ecommerceapi.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Clase entidad que representa un producto en el sistema de e-commerce.
 * 
 * <p>Esta clase se mapea a la tabla "products" en la base de datos y contiene
 * toda la información necesaria para la gestión de productos incluyendo nombre,
 * descripción, precio, cantidad de stock y categoría.</p>
 * 
 * <p>La clase utiliza anotaciones de Lombok para generar automáticamente getters,
 * setters, constructores y otro código repetitivo.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    /**
     * Identificador único para el producto.
     * Auto-generado usando estrategia de identidad de base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del producto.
     */
    private String name;
    
    /**
     * Descripción detallada del producto.
     */
    private String description;
    
    /**
     * Precio del producto en la moneda del sistema.
     */
    private Double price;
    
    /**
     * Cantidad de stock disponible del producto.
     */
    private Integer stock;
    
    /**
     * Categoría o clasificación del producto.
     */
    private String category;
}
