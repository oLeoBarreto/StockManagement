package com.barreto.stockmanagement.useCases.user;

import com.barreto.stockmanagement.infra.DTOs.user.UserSendWelcomeMailBody;

public interface UserUseCase {
    void sendWelcomeMail(UserSendWelcomeMailBody user);
}
