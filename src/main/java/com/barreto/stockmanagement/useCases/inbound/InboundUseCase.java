package com.barreto.stockmanagement.useCases.inbound;

import com.barreto.stockmanagement.domains.Inbound;
import com.barreto.stockmanagement.infra.DTOs.inbound.InboundPostRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InboundUseCase {
    Page<Inbound> listAll(Pageable pageable);
    Inbound findInboundById(String id);
    Inbound createInbound(InboundPostRequestBody inboundPostRequestBody);
    void deleteInbound(String id);
}
