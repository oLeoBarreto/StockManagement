package com.barreto.stockmanagement.controller.user;

import com.barreto.stockmanagement.domains.user.User;
import com.barreto.stockmanagement.infra.DTOs.user.UserUpdateLoginResponseBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserEndpoints {
    ResponseEntity<Page<User>> getUserListByCompany(String companyID, Pageable pageable);
    ResponseEntity<User> putUpdateUser(UserUpdateLoginResponseBody userUpdateLoginResponseBody);
}
