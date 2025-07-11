package com.alejandro.ecommerceapi.repository;

import com.alejandro.ecommerceapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

