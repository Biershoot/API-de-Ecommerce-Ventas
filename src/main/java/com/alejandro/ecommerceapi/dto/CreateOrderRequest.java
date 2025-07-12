package com.alejandro.ecommerceapi.dto;

import lombok.Data;

import java.util.List;

/**
 * Objeto de Transferencia de Datos (DTO) para solicitudes de creación de pedidos.
 * 
 * <p>Esta clase se utiliza para transferir datos de pedidos desde el cliente al servidor
 * cuando se crea un nuevo pedido. Contiene una lista de elementos de pedido, donde cada
 * elemento representa un producto y su cantidad solicitada.</p>
 * 
 * <p>La clase utiliza la anotación {@code @Data} de Lombok para generar automáticamente
 * getters, setters, toString, equals y métodos hashCode.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Data
public class CreateOrderRequest {
    /**
     * Lista de elementos del pedido que contienen productos y sus cantidades.
     * Cada elemento especifica un ID de producto y la cantidad a pedir.
     */
    private List<OrderItemRequest> items;
}

