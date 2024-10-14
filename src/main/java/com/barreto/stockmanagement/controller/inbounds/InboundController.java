package com.barreto.stockmanagement.controller.inbounds;

import com.barreto.stockmanagement.domains.documents.Inbound;
import com.barreto.stockmanagement.infra.DTOs.inbound.InboundPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.inbound.InboundStatusPutRequestBody;
import com.barreto.stockmanagement.infra.exceptions.BadRequestExceptionDetail;
import com.barreto.stockmanagement.useCases.inbound.InboundService;
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
@RequestMapping("/inbounds")
@RequiredArgsConstructor
@Tag(name = "Inbound", description = "Inbound document operations endpoints")
public class InboundController implements InboundEndpoints {

    private final InboundService inboundService;

    @GetMapping()
    @Operation(summary = "List all inbounds")
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
    public ResponseEntity<Page<Inbound>> getInboundList(@RequestParam String companyId, Pageable pageable) {
        return new ResponseEntity<>(inboundService.listAll(pageable, companyId), HttpStatus.OK);
    }

    @GetMapping("/findById")
    @Operation(summary = "Find a existing inbound by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Success found response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Inbound.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Inbound> getInboundById(@RequestParam String id) {
        return new ResponseEntity<>(inboundService.findInboundById(id), HttpStatus.FOUND);
    }

    @PostMapping()
    @Operation(summary = "Create a new inbound")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success create response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Inbound.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Inbound> postNewInbound(@RequestBody @Valid InboundPostRequestBody inboundPostRequestBody) {
        return new ResponseEntity<>(inboundService.createInbound(inboundPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/status")
    @Operation(summary = "Updating inbound status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success update response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Inbound.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Inbound> putInboundStatus(@RequestBody @Valid InboundStatusPutRequestBody inboundStatusPutRequestBody) {
        return new ResponseEntity<>(inboundService.updateInboundStatus(inboundStatusPutRequestBody), HttpStatus.OK);
    }

    @DeleteMapping()
    @Operation(summary = "Delete a existing inbound")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success delete response"),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Void> deleteInbound(@RequestParam String id) {
        inboundService.deleteInbound(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
