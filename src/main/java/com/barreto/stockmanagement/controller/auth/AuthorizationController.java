package com.barreto.stockmanagement.controller.auth;

import com.barreto.stockmanagement.domains.user.User;
import com.barreto.stockmanagement.infra.DTOs.user.UserLoginRequestBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserLoginResponseBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserRegisterRequestBody;
import com.barreto.stockmanagement.useCases.user.UserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController implements AuthorizationEndpoints {
    private final UserUseCase authorizationService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseBody> login(@RequestBody @Valid UserLoginRequestBody userLoginRequestBody) {
        return new ResponseEntity<>(authorizationService.loginUser(userLoginRequestBody), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequestBody userRegisterRequestBody) {
        return new ResponseEntity<>(authorizationService.registerUser(userRegisterRequestBody), HttpStatus.CREATED);
    }
}
