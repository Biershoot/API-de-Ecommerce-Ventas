package com.alejandro.ecommerceapi.dto;

import lombok.Data;

/**
 * Objeto de Transferencia de Datos (DTO) para solicitudes de elementos de pedido.
 * 
 * <p>Esta clase se utiliza para transferir datos de elementos de producto individuales
 * cuando se crean pedidos. Contiene el identificador del producto y la
 * cantidad solicitada para ese producto específico.</p>
 * 
 * <p>La clase utiliza la anotación {@code @Data} de Lombok para generar automáticamente
 * getters, setters, toString, equals y métodos hashCode.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Data
public class OrderItemRequest {
    /**
     * Identificador único del producto a pedir.
     * Debe referenciar un producto existente en el sistema.
     */
    private Long productId;
    
    /**
     * Cantidad del producto a pedir.
     * Debe ser mayor que cero y no exceder el stock disponible.
     */
    private Integer quantity;
}

