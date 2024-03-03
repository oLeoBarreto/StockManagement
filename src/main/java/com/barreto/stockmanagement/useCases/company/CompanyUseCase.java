package com.barreto.stockmanagement.useCases.company;

import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPutRequestBody;

public interface CompanyUseCase {
    Company findCompanyByIdJ(String id);
    Company findCompanyByCNPJ(String cnpj);
    Company createNewCompany(CompanyPostRequestBody companyPostRequestBody);
    Company updateCompany(String cnpj, CompanyPutRequestBody companyPutRequestBody);
    void deleteExistingCompany(String cnpj);
}
