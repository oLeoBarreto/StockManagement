package com.barreto.stockmanagement.controller.inbounds;

import com.barreto.stockmanagement.domains.documents.Inbound;
import com.barreto.stockmanagement.infra.DTOs.inbound.InboundPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.inbound.InboundStatusPutRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface InboundEndpoints {
    ResponseEntity<Page<Inbound>> getInboundList(String companyId, Pageable pageable);
    ResponseEntity<Inbound> getInboundById(String id);
    ResponseEntity<Inbound> postNewInbound(InboundPostRequestBody inboundPostRequestBody);
    ResponseEntity<Inbound> putInboundStatus(InboundStatusPutRequestBody inboundStatusPutRequestBody);
    ResponseEntity<Void> deleteInbound(String id);
}
