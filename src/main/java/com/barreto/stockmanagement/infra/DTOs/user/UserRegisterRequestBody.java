package com.barreto.stockmanagement.infra.DTOs.user;

import com.barreto.stockmanagement.domains.user.UserRole;

public record UserRegisterRequestBody(String login, String password, UserRole role) {

}
