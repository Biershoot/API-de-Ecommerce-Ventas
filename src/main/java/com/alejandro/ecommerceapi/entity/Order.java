package com.alejandro.ecommerceapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase entidad que representa un pedido en el sistema de e-commerce.
 * 
 * <p>Esta clase se mapea a la tabla "orders" en la base de datos y representa
 * un pedido de cliente que contiene múltiples productos. Cada pedido está asociado
 * con un usuario (cliente) y contiene una lista de detalles del pedido (productos y cantidades).</p>
 * 
 * <p>El pedido tiene un estado que rastrea su ciclo de vida (PENDING, CANCELLED)
 * e incluye el monto total y la marca de tiempo de creación.</p>
 * 
 * <p>La clase utiliza anotaciones de Lombok para generar automáticamente getters,
 * setters, constructores y otro código repetitivo.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    /**
     * Identificador único para el pedido.
     * Auto-generado usando estrategia de identidad de base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Marca de tiempo cuando se creó el pedido.
     */
    private LocalDateTime createdAt;

    /**
     * Monto total del pedido incluyendo todos los productos y cantidades.
     */
    private BigDecimal total;

    /**
     * El usuario (cliente) que realizó este pedido.
     * Muchos pedidos pueden pertenecer a un usuario.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User client;

    /**
     * Lista de detalles del pedido que contienen productos y sus cantidades.
     * Un pedido puede tener muchos detalles de pedido.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> details;

    /**
     * Estado actual del pedido (PENDING o CANCELLED).
     * Por defecto es PENDING cuando se crea un nuevo pedido.
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;
}

