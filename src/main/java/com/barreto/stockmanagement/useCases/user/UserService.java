package com.barreto.stockmanagement.useCases.user;

import com.barreto.stockmanagement.domains.user.User;
import com.barreto.stockmanagement.infra.DTOs.user.UserLoginRequestBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserLoginResponseBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserRegisterRequestBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserSendWelcomeMailBody;
import com.barreto.stockmanagement.infra.config.token.TokenManageService;
import com.barreto.stockmanagement.infra.database.repository.UserRepository;
import com.barreto.stockmanagement.infra.exceptions.BadRequestException;
import com.barreto.stockmanagement.useCases.company.CompanyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserRepository repository;
    private final TokenManageService tokenManageService;
    private final AuthenticationManager authenticationManager;
    private final SendMailUseCase sendMailUseCase;
    private final CompanyUseCase companyService;

    public UserLoginResponseBody loginUser(UserLoginRequestBody user) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(user.login(), user.password());
        Authentication authenticate = this.authenticationManager.authenticate(usernamePassword);

        return new UserLoginResponseBody(tokenManageService.generateToken((User) authenticate.getPrincipal()));
    }

    public User registerUser(UserRegisterRequestBody user) {
        if (this.repository.findByLogin(user.login()) != null) {
            throw new BadRequestException("User already exists with this email!");
        }

        var company = companyService.findCompanyByCNPJ(user.companyCNPJ());

        String encodedPassword = new BCryptPasswordEncoder().encode(user.password());
        User createUser = new User(user.login(), encodedPassword, user.name(), user.role(), company);

        sendMailUseCase.sendWelcomeMail(new UserSendWelcomeMailBody(user.login(), user.name()));

        return this.repository.save(createUser);
    }
}
