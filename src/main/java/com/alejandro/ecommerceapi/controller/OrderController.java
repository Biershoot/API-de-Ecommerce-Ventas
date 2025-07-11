package com.alejandro.ecommerceapi.controller;

import com.alejandro.ecommerceapi.dto.CreateOrderRequest;
import com.alejandro.ecommerceapi.entity.Order;
import com.alejandro.ecommerceapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // CLIENT crea una orden
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    // CLIENT ve sus órdenes
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/my")
    public ResponseEntity<List<Order>> myOrders() {
        return ResponseEntity.ok(orderService.getClientOrders());
    }

    // ADMIN ve todas las órdenes
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Order>> allOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}

