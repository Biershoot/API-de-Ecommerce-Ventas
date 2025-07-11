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

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

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

    public List<Order> getClientOrders() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User client = userRepository.findByEmail(email).orElseThrow();
        return orderRepository.findByClient(client);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}

