package com.barreto.stockmanagement.controller.outbounds;

import com.barreto.stockmanagement.domains.documents.Outbound;
import com.barreto.stockmanagement.infra.DTOs.outbound.OutboundPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.outbound.OutboundStatusPutRequestBody;
import com.barreto.stockmanagement.infra.exceptions.BadRequestExceptionDetail;
import com.barreto.stockmanagement.useCases.outbound.OutboundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/outbounds")
@RequiredArgsConstructor
@Tag(name = "Outbound", description = "Outbound document operations endpoints")
public class OutboundController implements OutboundEndpoints {
    private final OutboundService outboundService;

    @GetMapping()
    @Operation(summary = "List all outbounds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Page<Outbound>> getOutboundList(@RequestParam String companyId, Pageable pageable) {
        return new ResponseEntity<>(outboundService.listAll(companyId, pageable), HttpStatus.OK);
    }

    @GetMapping("/findById")
    @Operation(summary = "Found a existing outbound by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Success found response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Outbound.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Outbound> getOutboundById(@RequestParam String id) {
        return new ResponseEntity<>(outboundService.findOutboundById(id), HttpStatus.FOUND);
    }

    @PostMapping()
    @Operation(summary = "Found a existing outbound by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success create response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Outbound.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Outbound> postNewOutbound(@RequestBody @Valid OutboundPostRequestBody outboundPostRequestBody) {
        return new ResponseEntity<>(outboundService.createNewOutbound(outboundPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/status")
    @Operation(summary = "Update existing outbound status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success update response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Outbound.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Outbound> putInboundStatus(@RequestBody @Valid OutboundStatusPutRequestBody outboundStatusPutRequestBody) {
        return new ResponseEntity<>(outboundService.updateOutboundStatus(outboundStatusPutRequestBody), HttpStatus.OK);
    }

    @DeleteMapping()
    @Operation(summary = "Delete existing outbound")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success delete response"),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Void> deleteOutbound(@RequestParam String id) {
        outboundService.deleteOutbound(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
