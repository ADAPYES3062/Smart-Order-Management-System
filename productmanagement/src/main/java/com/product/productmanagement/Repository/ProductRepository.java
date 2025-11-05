package com.product.productmanagement.Repository;

import com.product.productmanagement.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}