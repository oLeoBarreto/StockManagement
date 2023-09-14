package com.barreto.stockmanagement.useCases.outbound;

import com.barreto.stockmanagement.domains.Outbound;
import com.barreto.stockmanagement.infra.DTOs.outbound.OutboundPostRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OutboundUseCase {
    Page<Outbound> listAll(Pageable pageable);
    Outbound findOutboundById(String id);
    Outbound createNewOutbound(OutboundPostRequestBody outboundPostRequestBody);
    void deleteOutbound(String id);
}
