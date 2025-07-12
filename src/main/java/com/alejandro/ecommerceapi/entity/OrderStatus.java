package com.alejandro.ecommerceapi.entity;

/**
 * Enumeración que representa los posibles estados de un pedido en el sistema de e-commerce.
 * 
 * <p>Esta enumeración define los estados del ciclo de vida que puede tener un pedido durante su procesamiento.
 * El estado ayuda a rastrear el estado actual de los pedidos y permite operaciones de filtrado y gestión.</p>
 * 
 * <p>Estados disponibles:</p>
 * <ul>
 *   <li><strong>PENDING</strong> - El pedido ha sido creado pero aún no ha sido procesado o enviado</li>
 *   <li><strong>CANCELLED</strong> - El pedido ha sido cancelado y no será procesado</li>
 * </ul>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
public enum OrderStatus {
    /**
     * El pedido está pendiente de procesamiento. Este es el estado por defecto para pedidos recién creados.
     */
    PENDING,
    
    /**
     * El pedido ha sido cancelado y no será procesado más.
     */
    CANCELLED
} 