package com.barreto.stockmanagement.infra.database.repository;

import com.barreto.stockmanagement.domains.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Optional<Company> findByCnpj(String cnpj);
    Optional<Company> findByEmail(String email);
}
