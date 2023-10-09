package com.barreto.stockmanagement.infra.DTOs.user;

import lombok.Data;

@Data
public class UserLoginRequestBody {
    private String login;
    private String password;
}
