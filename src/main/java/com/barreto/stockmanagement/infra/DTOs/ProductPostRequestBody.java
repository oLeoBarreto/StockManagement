package com.barreto.stockmanagement.infra.DTOs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductPostRequestBody {
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private String supplier;
    private String category;
}
