package com.barreto.stockmanagement.infra.database.repository;

import com.barreto.stockmanagement.domains.documents.Outbound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundRepository extends JpaRepository<Outbound, String> {
    Page<Outbound> findOutboundByCompanyId(String companyId, Pageable pageable);
}
