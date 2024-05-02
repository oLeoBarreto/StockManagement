package com.barreto.stockmanagement.infra.database.repository;

import com.barreto.stockmanagement.domains.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findByCategoryAndCompanyId(Pageable pageable, String category, String companyId);
    Page<Product> findBySupplierAndCompanyId(Pageable pageable, String supplier, String companyId);
    Page<Product> findByCompanyId(Pageable pageable, String companyId);
}
