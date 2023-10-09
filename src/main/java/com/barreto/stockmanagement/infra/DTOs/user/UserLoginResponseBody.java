package com.barreto.stockmanagement.infra.DTOs.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponseBody {
    private String token;
}
