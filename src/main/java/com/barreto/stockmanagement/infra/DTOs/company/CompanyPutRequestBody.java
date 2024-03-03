package com.barreto.stockmanagement.infra.DTOs.company;

public record CompanyPutRequestBody(String name, String email, String password) {
}
