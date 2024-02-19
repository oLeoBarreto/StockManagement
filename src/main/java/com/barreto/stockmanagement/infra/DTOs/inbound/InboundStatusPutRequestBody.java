package com.barreto.stockmanagement.infra.DTOs.inbound;

import com.barreto.stockmanagement.domains.documents.DocumentStatus;

public record InboundStatusPutRequestBody(String inboundId, DocumentStatus status) {
}
