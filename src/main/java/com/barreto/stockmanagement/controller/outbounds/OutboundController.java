package com.barreto.stockmanagement.controller.outbounds;

import com.barreto.stockmanagement.domains.Outbound;
import com.barreto.stockmanagement.infra.DTOs.outbound.OutboundPostRequestBody;
import com.barreto.stockmanagement.useCases.outbound.OutboundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("outbounds")
@RequiredArgsConstructor
public class OutboundController implements OutboundEndpoints {
    private final OutboundService outboundService;

    @GetMapping()
    public ResponseEntity<Page<Outbound>> getOutboundList(Pageable pageable) {
        return new ResponseEntity<>(outboundService.listAll(pageable), HttpStatus.OK);
    }

    @GetMapping("findById")
    public ResponseEntity<Outbound> getOutboundById(@RequestParam String id) {
        return new ResponseEntity<>(outboundService.findOutboundById(id), HttpStatus.FOUND);
    }

    @PostMapping()
    public ResponseEntity<Outbound> postNewOutbound(@RequestBody @Valid OutboundPostRequestBody outboundPostRequestBody) {
        return new ResponseEntity<>(outboundService.createNewOutbound(outboundPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteOutbound(@RequestParam String id) {
        outboundService.deleteOutbound(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
