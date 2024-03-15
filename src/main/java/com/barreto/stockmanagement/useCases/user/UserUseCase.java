package com.barreto.stockmanagement.useCases.user;

import com.barreto.stockmanagement.domains.user.User;
import com.barreto.stockmanagement.infra.DTOs.user.UserLoginRequestBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserLoginResponseBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserRegisterRequestBody;

public interface UserUseCase {
    UserLoginResponseBody loginUser(UserLoginRequestBody user);
    User registerUser(UserRegisterRequestBody user);
}
