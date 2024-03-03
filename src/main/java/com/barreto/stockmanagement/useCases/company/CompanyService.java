package com.barreto.stockmanagement.useCases.company;

import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPutRequestBody;
import com.barreto.stockmanagement.infra.database.repository.CompanyRepository;
import com.barreto.stockmanagement.infra.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService implements CompanyUseCase {
    private final CompanyRepository repository;

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
        if (repository.findByCnpj(companyPostRequestBody.cnpj()).isPresent()) {
            throw new BadRequestException("Already exists a company with this CNPJ!");
        }

        if (repository.findByEmail(companyPostRequestBody.email()).isPresent()) {
            throw new BadRequestException("Already exists a company with this email!");
        }

        var encodedPassword = new BCryptPasswordEncoder().encode(companyPostRequestBody.password());

        var company = new Company(
                companyPostRequestBody.cnpj(),
                companyPostRequestBody.name(),
                companyPostRequestBody.email(),
                encodedPassword
        );

        return repository.save(company);
    }

    public Company updateCompany(String cnpj, CompanyPutRequestBody companyPutRequestBody) {
        var existingCompany = findCompanyByCNPJ(cnpj);

        var encodedPassword = new BCryptPasswordEncoder().encode(companyPutRequestBody.password());

        existingCompany.setName(companyPutRequestBody.name());
        existingCompany.setEmail(companyPutRequestBody.email());
        existingCompany.setPassword(encodedPassword);

        return repository.save(existingCompany);
    }

    public void deleteExistingCompany(String cnpj) {
        var existingCompany = findCompanyByCNPJ(cnpj);

        repository.delete(existingCompany);
    }
}
