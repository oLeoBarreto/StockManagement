package com.barreto.stockmanagement.useCases.company;

import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPutRequestBody;

public interface CompanyUseCase {
    Company findCompanyById(String id);
    Company findCompanyByCNPJ(String cnpj);
    Company createNewCompany(CompanyPostRequestBody companyPostRequestBody);
    Company updateCompany(String cnpj, CompanyPutRequestBody companyPutRequestBody);
}
