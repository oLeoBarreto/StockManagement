package com.barreto.stockmanagement.useCases.user;

import com.barreto.stockmanagement.infra.DTOs.user.UserSendWelcomeMailBody;

public interface SendMailUseCase {
    void sendWelcomeMail(UserSendWelcomeMailBody user);
}
