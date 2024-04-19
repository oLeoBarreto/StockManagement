package com.barreto.stockmanagement.controller.user;

import com.barreto.stockmanagement.domains.user.User;
import com.barreto.stockmanagement.infra.DTOs.user.UserUpdateLoginResponseBody;
import com.barreto.stockmanagement.useCases.user.UserUseCase;
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
public class UserController implements UserEndpoints{
    private final UserUseCase userService;

    @GetMapping("/findByCompany")
    public ResponseEntity<Page<User>> getUserListByCompany(@RequestParam String companyID, Pageable pageable) {
        return new ResponseEntity<>(userService.listAllUserByCompany(companyID, pageable), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<User> putUpdateUser(@RequestBody @Valid UserUpdateLoginResponseBody userUpdateLoginResponseBody) {
        return new ResponseEntity<>(userService.updateUserLogin(userUpdateLoginResponseBody), HttpStatus.OK);
    }
}
