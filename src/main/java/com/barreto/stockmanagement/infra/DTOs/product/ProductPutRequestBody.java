package com.barreto.stockmanagement.infra.DTOs.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductPutRequestBody {
    private String id;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private String supplier;
    private String category;
}
