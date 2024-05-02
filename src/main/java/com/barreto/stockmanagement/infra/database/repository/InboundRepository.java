package com.barreto.stockmanagement.infra.database.repository;

import com.barreto.stockmanagement.domains.documents.Inbound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundRepository extends JpaRepository<Inbound, String> {
    Page<Inbound> findAllByCompanyId(String companyId, Pageable pageable);
}
