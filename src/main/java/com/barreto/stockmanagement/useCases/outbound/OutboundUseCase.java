package com.barreto.stockmanagement.useCases.outbound;

import com.barreto.stockmanagement.domains.documents.Outbound;
import com.barreto.stockmanagement.infra.DTOs.outbound.OutboundPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.outbound.OutboundStatusPutRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OutboundUseCase {
    Page<Outbound> listAll(String companyId, Pageable pageable);
    Outbound findOutboundById(String id);
    Outbound createNewOutbound(OutboundPostRequestBody outboundPostRequestBody);
    Outbound updateOutboundStatus(OutboundStatusPutRequestBody outboundStatusPutRequestBody);
    void deleteOutbound(String id);
}
