package com.barreto.stockmanagement.infra.DTOs.product;

import java.math.BigDecimal;

public record ProductPostRequestBody(String name, String description, BigDecimal unitPrice, String supplier, String category, String companyId) {

}
