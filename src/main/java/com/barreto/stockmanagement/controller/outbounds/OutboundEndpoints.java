package com.barreto.stockmanagement.controller.outbounds;

import com.barreto.stockmanagement.domains.Outbound;
import com.barreto.stockmanagement.infra.DTOs.outbound.OutboundPostRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface OutboundEndpoints {
    ResponseEntity<Page<Outbound>> getOutboundList(Pageable pageable);
    ResponseEntity<Outbound> getOutboundById(String id);
    ResponseEntity<Outbound> postNewOutbound(OutboundPostRequestBody outboundPostRequestBody);
    ResponseEntity<Void> deleteOutbound(String id);
}
