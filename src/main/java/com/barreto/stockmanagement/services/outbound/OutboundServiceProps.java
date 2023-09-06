package com.barreto.stockmanagement.services.outbound;

import com.barreto.stockmanagement.domains.Outbound;
import com.barreto.stockmanagement.infra.DTOs.OutboundPostRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OutboundServiceProps {
    Page<Outbound> listAll(Pageable pageable);
    Outbound findOutboundById(String id);
    Outbound createNewOutbound(OutboundPostRequestBody outboundPostRequestBody);
    void deleteOutbound(String id);
}
