package com.alejandro.ecommerceapi.dto;

import lombok.Data;

/**
 * Objeto de Transferencia de Datos (DTO) para solicitudes de creación y actualización de productos.
 * 
 * <p>Esta clase se utiliza para transferir datos de productos desde el cliente al servidor
 * cuando se crean nuevos productos o se actualizan los existentes. Contiene toda la
 * información necesaria para la gestión de productos incluyendo nombre, descripción,
 * precio, cantidad de stock y categoría.</p>
 * 
 * <p>La clase utiliza la anotación {@code @Data} de Lombok para generar automáticamente
 * getters, setters, toString, equals y métodos hashCode.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Data
public class ProductRequest {
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