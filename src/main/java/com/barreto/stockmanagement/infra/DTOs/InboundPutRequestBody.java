package com.barreto.stockmanagement.infra.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class InboundPutRequestBody {
    @NotNull(message = "Inbound ID could not be null")
    @NotEmpty(message = "Inbound ID could not be empty")
    private String inboundId;

    @NotNull(message = "Quantity could not be null")
    @PositiveOrZero(message = "Quantity price must be a positive value")
    private Float quantity;

    @NotNull(message = "Product ID could not be null")
    @NotEmpty(message = "Product ID could not be empty")
    private String productId;
}
