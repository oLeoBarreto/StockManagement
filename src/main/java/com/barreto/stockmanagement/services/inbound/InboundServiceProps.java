package com.barreto.stockmanagement.services.inbound;

import com.barreto.stockmanagement.domains.Inbound;
import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.InboundPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.InboundPutRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InboundServiceProps {
    Page<Inbound> listAll(Pageable pageable);
    Inbound findInboundById(String id);
    Inbound createInbound(InboundPostRequestBody inboundPostRequestBody);
    void deleteInbound(String id);
}
