package com.barreto.stockmanagement.infra.DTOs.product;


import java.math.BigDecimal;

public record ProductPutRequestBody(String id, String name, String description, BigDecimal unitPrice, String supplier, String category) {

}
