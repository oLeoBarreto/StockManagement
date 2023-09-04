package com.barreto.stockmanagement.infra.database.repository;

import com.barreto.stockmanagement.domains.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findByCategory(Pageable pageable, String category);
    Page<Product> findBySupplier(Pageable pageable, String supplier);
}
