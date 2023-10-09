package com.barreto.stockmanagement.infra.config.token;

import com.barreto.stockmanagement.domains.user.User;

public interface TokenManageUseCase {
    String generateToken(User user);
    String validateToke(String token);
}
