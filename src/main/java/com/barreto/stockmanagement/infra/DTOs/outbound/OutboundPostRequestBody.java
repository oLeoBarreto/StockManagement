package com.barreto.stockmanagement.infra.DTOs.outbound;

public record OutboundPostRequestBody(Float quantity, String productId) {
}
