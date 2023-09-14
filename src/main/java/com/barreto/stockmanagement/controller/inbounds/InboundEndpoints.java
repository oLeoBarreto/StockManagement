package com.barreto.stockmanagement.controller.inbounds;

import com.barreto.stockmanagement.domains.Inbound;
import com.barreto.stockmanagement.infra.DTOs.inbound.InboundPostRequestBody;
;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface InboundEndpoints {
    ResponseEntity<Page<Inbound>> getInboundList(Pageable pageable);
    ResponseEntity<Inbound> getInboundById(String id);
    ResponseEntity<Inbound> postNewInbound(InboundPostRequestBody inboundPostRequestBody);
    ResponseEntity<Void> deleteInbound(String id);
}
