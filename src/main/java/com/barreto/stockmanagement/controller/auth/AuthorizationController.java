package com.barreto.stockmanagement.controller.auth;

import com.barreto.stockmanagement.domains.user.User;
import com.barreto.stockmanagement.infra.DTOs.user.UserLoginRequestBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserLoginResponseBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserRegisterRequestBody;
import com.barreto.stockmanagement.infra.config.token.TokenManageService;
import com.barreto.stockmanagement.infra.database.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthorizationController implements AuthorizationEndpoints {
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final TokenManageService tokenManageService;

    @PostMapping("login")
    public ResponseEntity<UserLoginResponseBody> login(@RequestBody @Valid UserLoginRequestBody user) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(user.login(), user.password());
        Authentication authenticate = this.authenticationManager.authenticate(usernamePassword);

        return new ResponseEntity<>(new UserLoginResponseBody(tokenManageService.generateToken((User) authenticate.getPrincipal())), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequestBody user) {
        if (this.repository.findByLogin(user.login()) != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = new BCryptPasswordEncoder().encode(user.password());
        User createUser = new User(user.login(), encodedPassword, user.role());

        return new ResponseEntity<>(this.repository.save(createUser), HttpStatus.CREATED);
    }
}
