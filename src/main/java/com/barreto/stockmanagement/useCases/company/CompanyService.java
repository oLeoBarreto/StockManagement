package com.barreto.stockmanagement.useCases.company;

import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPutRequestBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserRegisterRequestBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserUpdateLoginResponseBody;
import com.barreto.stockmanagement.infra.database.repository.CompanyRepository;
import com.barreto.stockmanagement.infra.exceptions.BadRequestException;
import com.barreto.stockmanagement.useCases.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.barreto.stockmanagement.domains.user.UserRole.ADMIN;

@Service
@RequiredArgsConstructor
public class CompanyService implements CompanyUseCase {
    private final CompanyRepository repository;
    private final UserUseCase userService;

    public Company findCompanyByIdJ(String id) {
        return repository.findById(id).orElseThrow(
                () -> new BadRequestException("Not found a company with this Id!")
        );
    }

    public Company findCompanyByCNPJ(String cnpj) {
        return repository.findByCnpj(cnpj).orElseThrow(
                () -> new BadRequestException("Not exists a company with that CNPJ!")
        );
    }

    public Company createNewCompany(CompanyPostRequestBody companyPostRequestBody) {
        verifyIfCnpjIsRegistered(companyPostRequestBody.cnpj());

        verifyIfEmailIsRegistered(companyPostRequestBody.email());

        var encodedPassword = new BCryptPasswordEncoder().encode(companyPostRequestBody.password());

        var company = new Company(
                companyPostRequestBody.cnpj(),
                companyPostRequestBody.name(),
                companyPostRequestBody.email(),
                encodedPassword
        );

        var savedCompany = repository.save(company);

        userService.registerUser(
                new UserRegisterRequestBody(
                        company.getEmail(), company.getPassword(), company.getName(), ADMIN, company.getCnpj()
                )
        );

        return savedCompany;
    }

    public Company updateCompany(String cnpj, CompanyPutRequestBody companyPutRequestBody) {
        var existingCompany = findCompanyByCNPJ(cnpj);

        var encodedPassword = new BCryptPasswordEncoder().encode(companyPutRequestBody.password());

        existingCompany.setName(companyPutRequestBody.name());
        existingCompany.setEmail(companyPutRequestBody.email());
        existingCompany.setPassword(encodedPassword);

        var savedCompany = repository.save(existingCompany);

        userService.updateUserLogin(
                new UserUpdateLoginResponseBody(
                        savedCompany.getName(), savedCompany.getEmail(), savedCompany.getPassword(), ADMIN, savedCompany.getCnpj()
                )
        );

        return savedCompany;
    }

    private void verifyIfEmailIsRegistered(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new BadRequestException("Already exists a company with this email!");
        }
    }

    private void verifyIfCnpjIsRegistered(String cnpj) {
        if (repository.findByCnpj(cnpj).isPresent()) {
            throw new BadRequestException("Already exists a company with this CNPJ!");
        }
    }
}
