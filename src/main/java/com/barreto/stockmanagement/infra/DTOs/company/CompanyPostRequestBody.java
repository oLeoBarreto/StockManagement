package com.barreto.stockmanagement.infra.DTOs.company;

public record CompanyPostRequestBody(String cnpj, String name, String email, String password) {
}
