package com.barreto.stockmanagement.controller.outbounds;

import com.barreto.stockmanagement.domains.documents.Outbound;
import com.barreto.stockmanagement.infra.DTOs.outbound.OutboundPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.outbound.OutboundStatusPutRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface OutboundEndpoints {
    ResponseEntity<Page<Outbound>> getOutboundList(String companyId, Pageable pageable);
    ResponseEntity<Outbound> getOutboundById(String id);
    ResponseEntity<Outbound> postNewOutbound(OutboundPostRequestBody outboundPostRequestBody);
    ResponseEntity<Outbound> putInboundStatus(OutboundStatusPutRequestBody outboundStatusPutRequestBody);
    ResponseEntity<Void> deleteOutbound(String id);
}
