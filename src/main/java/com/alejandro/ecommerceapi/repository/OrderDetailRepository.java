package com.alejandro.ecommerceapi.repository;

import com.alejandro.ecommerceapi.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
