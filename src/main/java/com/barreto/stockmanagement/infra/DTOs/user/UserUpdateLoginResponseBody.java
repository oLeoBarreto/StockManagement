package com.barreto.stockmanagement.infra.DTOs.user;

import com.barreto.stockmanagement.domains.user.UserRole;

public record UserUpdateLoginResponseBody(String name, String login, String password, UserRole role, String companyCNPJ) {
}
