package com.barreto.stockmanagement.controller.user;

import com.barreto.stockmanagement.domains.user.User;
import com.barreto.stockmanagement.infra.DTOs.user.UserUpdateLoginResponseBody;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User operations endpoints")
public class UserController implements UserEndpoints{
    private final UserUseCase userService;

    @GetMapping("/findByCompany")
    @Operation(summary = "List all users by your company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Page<User>> getUserListByCompany(@RequestParam String companyID, Pageable pageable) {
        return new ResponseEntity<>(userService.listAllUserByCompany(companyID, pageable), HttpStatus.OK);
    }

    @PutMapping()
    @Operation(summary = "Update existing user data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success update response",
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
    public ResponseEntity<User> putUpdateUser(@RequestBody @Valid UserUpdateLoginResponseBody userUpdateLoginResponseBody) {
        return new ResponseEntity<>(userService.updateUserLogin(userUpdateLoginResponseBody), HttpStatus.OK);
    }
}
