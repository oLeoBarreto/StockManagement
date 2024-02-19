package com.barreto.stockmanagement.infra.DTOs.outbound;

import com.barreto.stockmanagement.domains.documents.DocumentStatus;

public record OutboundStatusPutRequestBody(String outboundId, DocumentStatus status) {
}
