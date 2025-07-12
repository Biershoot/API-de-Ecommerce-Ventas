package com.alejandro.ecommerceapi.controller;

import com.alejandro.ecommerceapi.dto.CreateOrderRequest;
import com.alejandro.ecommerceapi.entity.Order;
import com.alejandro.ecommerceapi.entity.OrderStatus;
import com.alejandro.ecommerceapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para operaciones relacionadas con pedidos.
 * 
 * <p>Este controlador proporciona endpoints para gestionar pedidos incluyendo:
 * <ul>
 *   <li>Creación de nuevos pedidos por clientes</li>
 *   <li>Consulta de pedidos del cliente autenticado</li>
 *   <li>Consulta de todos los pedidos (solo administradores)</li>
 *   <li>Cancelación de pedidos por clientes</li>
 *   <li>Filtrado de pedidos por estado</li>
 * </ul>
 * </p>
 * 
 * <p>Todos los endpoints requieren autenticación. Los clientes solo pueden
 * acceder a sus propios pedidos, mientras que los administradores pueden
 * acceder a todos los pedidos del sistema.</p>
 * 
 * <p>Base URL: {@code /api/orders}</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Crea un nuevo pedido para el usuario autenticado.
     * 
     * <p>Este endpoint permite a un cliente crear un nuevo pedido con los productos
     * especificados. El sistema valida automáticamente la disponibilidad de stock
     * y reduce las cantidades correspondientes. Solo los clientes pueden crear pedidos.</p>
     * 
     * <p>Ejemplo de solicitud:</p>
     * <pre>
     * {
     *   "items": [
     *     {
     *       "productId": 1,
     *       "quantity": 2
     *     },
     *     {
     *       "productId": 3,
     *       "quantity": 1
     *     }
     *   ]
     * }
     * </pre>
     * 
     * @param request La solicitud que contiene los elementos del pedido
     * @return ResponseEntity con el pedido creado
     */
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    /**
     * Recupera todos los pedidos del cliente autenticado.
     * 
     * <p>Este endpoint devuelve todos los pedidos que pertenecen al usuario
     * actualmente autenticado. Solo los clientes pueden acceder a sus propios pedidos.</p>
     * 
     * @return ResponseEntity con la lista de pedidos del cliente
     */
    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<Order>> getMyOrders() {
        return ResponseEntity.ok(orderService.getClientOrders());
    }

    /**
     * Recupera todos los pedidos en el sistema (solo administradores).
     * 
     * <p>Este endpoint devuelve todos los pedidos del sistema, independientemente
     * del cliente que los haya realizado. Solo los administradores pueden acceder
     * a esta funcionalidad.</p>
     * 
     * @return ResponseEntity con la lista de todos los pedidos
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * Recupera todos los pedidos con filtrado opcional por estado (solo administradores).
     * 
     * <p>Este endpoint permite a los administradores filtrar pedidos por estado.
     * Si no se proporciona un estado, se devuelven todos los pedidos.</p>
     * 
     * <p>Ejemplo de uso:</p>
     * <ul>
     *   <li>{@code GET /api/orders/all} - Todos los pedidos</li>
     *   <li>{@code GET /api/orders/all?status=PENDING} - Solo pedidos pendientes</li>
     *   <li>{@code GET /api/orders/all?status=CANCELLED} - Solo pedidos cancelados</li>
     * </ul>
     * 
     * @param status Filtro opcional de estado para los pedidos
     * @return ResponseEntity con la lista de pedidos filtrados
     */
    @GetMapping("/all/filter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam(required = false) OrderStatus status) {
        return ResponseEntity.ok(orderService.getAllOrders(Optional.ofNullable(status)));
    }

    /**
     * Recupera pedidos de un cliente específico con filtrado opcional por estado (solo administradores).
     * 
     * <p>Este endpoint permite a los administradores consultar los pedidos de un
     * cliente específico, con opción de filtrar por estado.</p>
     * 
     * <p>Ejemplo de uso:</p>
     * <ul>
     *   <li>{@code GET /api/orders/client/juan@example.com} - Todos los pedidos del cliente</li>
     *   <li>{@code GET /api/orders/client/juan@example.com?status=PENDING} - Pedidos pendientes del cliente</li>
     * </ul>
     * 
     * @param clientEmail El email del cliente cuyos pedidos se van a consultar
     * @param status Filtro opcional de estado para los pedidos
     * @return ResponseEntity con la lista de pedidos del cliente
     */
    @GetMapping("/client/{clientEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getClientOrders(
            @PathVariable String clientEmail,
            @RequestParam(required = false) OrderStatus status) {
        return ResponseEntity.ok(orderService.getClientOrders(clientEmail, Optional.ofNullable(status)));
    }

    /**
     * Cancela un pedido específico por el cliente que lo realizó.
     * 
     * <p>Este endpoint permite a un cliente cancelar su propio pedido, pero solo
     * si el pedido tiene estado PENDING. Los pedidos cancelados no pueden ser
     * cancelados nuevamente.</p>
     * 
     * @param orderId El ID del pedido a cancelar
     * @param authentication El objeto de autenticación para obtener el email del cliente
     * @return ResponseEntity con el pedido cancelado
     */
    @PostMapping("/{orderId}/cancel")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId, Authentication authentication) {
        String clientEmail = authentication.getName();
        return ResponseEntity.ok(orderService.cancelOrderByClient(orderId, clientEmail));
    }
}

