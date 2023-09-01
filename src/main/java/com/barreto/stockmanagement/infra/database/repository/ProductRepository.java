package com.barreto.stockmanagement.infra.database.repository;

import com.barreto.stockmanagement.domains.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByCategory(String category);
    Product findBySupplier(String supplier);
}
