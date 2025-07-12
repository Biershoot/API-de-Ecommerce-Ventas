package com.alejandro.ecommerceapi.service;

import com.alejandro.ecommerceapi.dto.CreateOrderRequest;
import com.alejandro.ecommerceapi.dto.OrderItemRequest;
import com.alejandro.ecommerceapi.entity.*;
import com.alejandro.ecommerceapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Optional;

/**
 * Clase de servicio para manejar operaciones de negocio relacionadas con pedidos.
 * 
 * <p>Este servicio proporciona métodos para gestionar pedidos incluyendo creación,
 * cancelación y recuperación con varias opciones de filtrado. Maneja lógica de negocio
 * compleja como gestión de inventario, cálculo de totales y gestión del estado de pedidos.</p>
 * 
 * <p>El servicio se integra con múltiples repositorios:
 * <ul>
 *   <li>{@link ProductRepository} - Para gestión de productos e inventario</li>
 *   <li>{@link OrderRepository} - Para persistencia de pedidos</li>
 *   <li>{@link UserRepository} - Para identificación de usuarios</li>
 * </ul>
 * </p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    /**
     * Crea un nuevo pedido para el usuario autenticado.
     * 
     * <p>Este método crea un pedido completo con los siguientes pasos:
     * <ol>
     *   <li>Identifica al usuario autenticado</li>
     *   <li>Valida la disponibilidad y stock de productos</li>
     *   <li>Reduce las cantidades de stock de productos</li>
     *   <li>Calcula subtotales y monto total</li>
     *   <li>Crea detalles del pedido y guarda el pedido</li>
     * </ol>
     * </p>
     * 
     * @param request La solicitud de creación de pedido que contiene elementos de producto
     * @return El pedido creado con todos los detalles
     * @throws RuntimeException si un producto no se encuentra o tiene stock insuficiente
     */
    public Order createOrder(CreateOrderRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User client = userRepository.findByEmail(email).orElseThrow();

        List<OrderDetail> details = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            // Descontar stock
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            BigDecimal subtotal = BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(item.getQuantity()));

            OrderDetail detail = OrderDetail.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .subtotal(subtotal)
                    .build();

            total = total.add(subtotal);
            details.add(detail);
        }

        Order order = Order.builder()
                .client(client)
                .createdAt(LocalDateTime.now())
                .total(total)
                .details(new ArrayList<>()) // se llena abajo
                .build();

        order = orderRepository.save(order);

        for (OrderDetail detail : details) {
            detail.setOrder(order);
        }

        order.setDetails(details);
        return orderRepository.save(order);
    }

    /**
     * Recupera todos los pedidos para el usuario actualmente autenticado.
     * 
     * @return Una lista de pedidos pertenecientes al usuario autenticado
     */
    public List<Order> getClientOrders() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User client = userRepository.findByEmail(email).orElseThrow();
        return orderRepository.findByClient(client);
    }

    /**
     * Recupera todos los pedidos en el sistema (funcionalidad de administrador).
     * 
     * @return Una lista de todos los pedidos en el sistema
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Cancela un pedido por el cliente que lo realizó.
     * 
     * <p>Este método permite a un cliente cancelar su propio pedido, pero solo si:
     * <ul>
     *   <li>El pedido pertenece al cliente especificado</li>
     *   <li>El estado del pedido es PENDING</li>
     * </ul>
     * </p>
     * 
     * @param orderId El ID del pedido a cancelar
     * @param clientEmail El email del cliente que intenta cancelar
     * @return El pedido cancelado
     * @throws RuntimeException si el pedido no existe, no pertenece al cliente,
     *         o no puede ser cancelado debido a su estado
     */
    public Order cancelOrderByClient(Long orderId, String clientEmail) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (!order.getClient().getEmail().equals(clientEmail)) {
            throw new RuntimeException("No puedes cancelar este pedido");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Solo se pueden cancelar pedidos con estado PENDING");
        }

        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    /**
     * Recupera todos los pedidos con filtrado opcional por estado.
     * 
     * @param status Filtro opcional de estado para los pedidos
     * @return Una lista de pedidos que coinciden con el filtro de estado, o todos los pedidos si no se proporciona filtro
     */
    public List<Order> getAllOrders(Optional<OrderStatus> status) {
        return status.map(orderRepository::findByStatus)
                     .orElse(orderRepository.findAll());
    }

    /**
     * Recupera pedidos para un cliente específico con filtrado opcional por estado.
     * 
     * @param clientEmail El email del cliente cuyos pedidos se van a recuperar
     * @param status Filtro opcional de estado para los pedidos
     * @return Una lista de pedidos pertenecientes al cliente, filtrados por estado si se proporciona
     */
    public List<Order> getClientOrders(String clientEmail, Optional<OrderStatus> status) {
        User client = userRepository.findByEmail(clientEmail).orElseThrow();
        return status.map(s -> orderRepository.findByClientAndStatus(client, s))
                     .orElse(orderRepository.findByClient(client));
    }
}

