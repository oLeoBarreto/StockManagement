package com.barreto.stockmanagement.infra.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OutboundPostRequestBody {
    @NotNull(message = "Quantity could not be null")
    @Positive(message = "Quantity price must be more than 0")
    private Float quantity;

    @NotNull(message = "Product ID could not be null")
    @NotEmpty(message = "Product ID could not be empty")
    private String productId;
}
