package com.barreto.stockmanagement.useCases.user;

import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.domains.user.User;
import com.barreto.stockmanagement.infra.DTOs.user.*;
import com.barreto.stockmanagement.infra.config.token.TokenManageService;
import com.barreto.stockmanagement.infra.database.repository.CompanyRepository;
import com.barreto.stockmanagement.infra.database.repository.UserRepository;
import com.barreto.stockmanagement.infra.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final CompanyRepository companyRepository;

    public UserLoginResponseBody loginUser(UserLoginRequestBody user) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(user.login(), user.password());
        Authentication authenticate = this.authenticationManager.authenticate(usernamePassword);

        return new UserLoginResponseBody(tokenManageService.generateToken((User) authenticate.getPrincipal()));
    }

    public User registerUser(UserRegisterRequestBody user) {
        if (this.repository.findByLogin(user.login()) != null) {
            throw new BadRequestException("User already exists with this email!");
        }

        var company = getCompany(user.companyCNPJ());

        String encodedPassword = new BCryptPasswordEncoder().encode(user.password());
        User createUser = new User(user.login(), encodedPassword, user.name(), user.role(), company);

        sendMailUseCase.sendWelcomeMail(new UserSendWelcomeMailBody(user.login(), user.name()));

        return this.repository.save(createUser);
    }

    public User updateUserLogin(UserUpdateLoginResponseBody user) {
        var company = getCompany(user.companyCNPJ());
        var findUser = this.repository.findByLoginAndCompany(user.login(), company.getId());

        if (findUser.getUsername().isEmpty()) {
            throw new BadRequestException("User not found!");
        }

        findUser.setLogin(user.login());
        findUser.setPassword(user.password());
        findUser.setName(user.name());
        findUser.setRole(user.role());

        return this.repository.save(findUser);
    }

    public Page<User> listAllUserByCompany(String companyID, Pageable pageable) {
        return this.repository.findByCompany(companyID, pageable);
    }

    private Company getCompany(String cnpj) {
        return companyRepository.findByCnpj(cnpj).orElseThrow(() -> new BadRequestException("Not exists a company with that CNPJ!"));
    }
}
