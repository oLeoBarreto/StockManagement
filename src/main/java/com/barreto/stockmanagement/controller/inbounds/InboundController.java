package com.barreto.stockmanagement.controller.inbounds;

import com.barreto.stockmanagement.domains.documents.Inbound;
import com.barreto.stockmanagement.infra.DTOs.inbound.InboundPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.inbound.InboundStatusPutRequestBody;
import com.barreto.stockmanagement.useCases.inbound.InboundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inbounds")
@RequiredArgsConstructor
public class InboundController implements InboundEndpoints {

    private final InboundService inboundService;

    @GetMapping()
    public ResponseEntity<Page<Inbound>> getInboundList(@RequestParam String companyId, Pageable pageable) {
        return new ResponseEntity<>(inboundService.listAll(pageable, companyId), HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<Inbound> getInboundById(@RequestParam String id) {
        return new ResponseEntity<>(inboundService.findInboundById(id), HttpStatus.FOUND);
    }

    @PostMapping()
    public ResponseEntity<Inbound> postNewInbound(@RequestBody @Valid InboundPostRequestBody inboundPostRequestBody) {
        return new ResponseEntity<>(inboundService.createInbound(inboundPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/status")
    public ResponseEntity<Inbound> putInboundStatus(@RequestBody @Valid InboundStatusPutRequestBody inboundStatusPutRequestBody) {
        return new ResponseEntity<>(inboundService.updateInboundStatus(inboundStatusPutRequestBody), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteInbound(@RequestParam String id) {
        inboundService.deleteInbound(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
