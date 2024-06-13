package com.barreto.stockmanagement.infra.DTOs.user;

import com.barreto.stockmanagement.domains.user.User;

public record UserLoginResponseBody(String token, User user) {

}
