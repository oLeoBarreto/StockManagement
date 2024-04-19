package com.barreto.stockmanagement.useCases.user;

import com.barreto.stockmanagement.domains.user.User;
import com.barreto.stockmanagement.infra.DTOs.user.UserLoginRequestBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserLoginResponseBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserRegisterRequestBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserUpdateLoginResponseBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserUseCase {
    UserLoginResponseBody loginUser(UserLoginRequestBody user);
    User registerUser(UserRegisterRequestBody user);
    User updateUserLogin(UserUpdateLoginResponseBody user);
    Page<User> listAllUserByCompany(String companyID, Pageable pageable);
}
