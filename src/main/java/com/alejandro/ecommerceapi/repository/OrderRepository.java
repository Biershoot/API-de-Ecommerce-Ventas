package com.alejandro.ecommerceapi.repository;

import com.alejandro.ecommerceapi.entity.Order;
import com.alejandro.ecommerceapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClient(User client);
}



