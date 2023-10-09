package com.barreto.stockmanagement.controller.auth;

import com.barreto.stockmanagement.domains.user.User;
import com.barreto.stockmanagement.infra.DTOs.user.UserLoginRequestBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserLoginResponseBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserRegisterRequestBody;
import org.springframework.http.ResponseEntity;

public interface AuthorizationEndpoints {
    ResponseEntity<UserLoginResponseBody> login(UserLoginRequestBody user);
    ResponseEntity<User> register(UserRegisterRequestBody user);
}
