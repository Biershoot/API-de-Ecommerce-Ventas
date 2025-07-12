package com.alejandro.ecommerceapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Clase entidad que representa un detalle de pedido en el sistema de e-commerce.
 * 
 * <p>Esta clase se mapea a la tabla "order_details" en la base de datos y representa
 * un elemento de línea de producto individual dentro de un pedido. Cada detalle de pedido contiene
 * información sobre un producto específico, su cantidad y el subtotal para ese elemento.</p>
 * 
 * <p>Esta entidad sirve como tabla de unión entre pedidos y productos,
 * permitiendo relaciones muchos a muchos con atributos adicionales como
 * cantidad y subtotal.</p>
 * 
 * <p>La clase utiliza anotaciones de Lombok para generar automáticamente getters,
 * setters, constructores y otro código repetitivo.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {

    /**
     * Identificador único para el detalle del pedido.
     * Auto-generado usando estrategia de identidad de base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Cantidad del producto pedido.
     */
    private Integer quantity;

    /**
     * Subtotal para este elemento de línea de producto específico.
     * Calculado como precio del producto multiplicado por cantidad.
     */
    private BigDecimal subtotal;

    /**
     * El pedido al que pertenece este detalle.
     * Muchos detalles de pedido pueden pertenecer a un pedido.
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * El producto incluido en este detalle de pedido.
     * Muchos detalles de pedido pueden referenciar el mismo producto.
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}

