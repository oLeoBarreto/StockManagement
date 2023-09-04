package com.barreto.stockmanagement.infra.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductPostRequestBody {
    @NotEmpty(message = "Name could not be empty")
    @NotNull(message = "Name could not be null")
    private String name;

    @NotEmpty(message = "Description could not be empty")
    @NotNull(message = "Description could not be null")
    private String description;

    @NotNull(message = "Unit price could not be null")
    private BigDecimal unitPrice;

    @NotEmpty(message = "Supplier could not be empty")
    @NotNull(message = "Supplier could not be null")
    private String supplier;

    @NotEmpty(message = "Category could not be empty")
    @NotNull(message = "Category could not be null")
    private String category;
}
