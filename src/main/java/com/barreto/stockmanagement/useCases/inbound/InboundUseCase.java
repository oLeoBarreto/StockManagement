package com.barreto.stockmanagement.useCases.inbound;

import com.barreto.stockmanagement.domains.documents.Inbound;
import com.barreto.stockmanagement.infra.DTOs.inbound.InboundPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.inbound.InboundStatusPutRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InboundUseCase {
    Page<Inbound> listAll(Pageable pageable, String companyId);
    Inbound findInboundById(String id);
    Inbound createInbound(InboundPostRequestBody inboundPostRequestBody);
    Inbound updateInboundStatus(InboundStatusPutRequestBody inboundStatusPutRequestBody);
    void deleteInbound(String id);
}
