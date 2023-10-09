package com.barreto.stockmanagement.infra.DTOs.user;

import com.barreto.stockmanagement.domains.user.UserRole;
import lombok.Data;

@Data
public class UserRegisterRequestBody {
    private String login;
    private String password;
    private UserRole role;
}
