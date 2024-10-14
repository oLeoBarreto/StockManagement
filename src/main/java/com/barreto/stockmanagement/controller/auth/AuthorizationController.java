package com.barreto.stockmanagement.controller.auth;

import com.barreto.stockmanagement.domains.user.User;
import com.barreto.stockmanagement.infra.DTOs.user.UserLoginRequestBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserLoginResponseBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserRegisterRequestBody;
import com.barreto.stockmanagement.infra.exceptions.BadRequestExceptionDetail;
import com.barreto.stockmanagement.useCases.user.UserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authorization", description = "Authorization endpoints")
public class AuthorizationController implements AuthorizationEndpoints {
    private final UserUseCase authorizationService;

    @PostMapping("/login")
    @Operation(summary = "Login user into the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success user login response",
                content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserLoginResponseBody.class))
                }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<UserLoginResponseBody> login(@RequestBody @Valid UserLoginRequestBody userLoginRequestBody) {
        return new ResponseEntity<>(authorizationService.loginUser(userLoginRequestBody), HttpStatus.OK);
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user into the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success user register response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequestBody userRegisterRequestBody) {
        return new ResponseEntity<>(authorizationService.registerUser(userRegisterRequestBody), HttpStatus.CREATED);
    }
}
