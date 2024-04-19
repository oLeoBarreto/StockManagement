package com.barreto.stockmanagement.controller.company;

import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPutRequestBody;
import org.springframework.http.ResponseEntity;

public interface CompanyEndpoints {
    ResponseEntity<Company> getCompanyByCNPJ(String cnpj);
    ResponseEntity<Company> getCompanyById(String id);
    ResponseEntity<Company> postNewCompany(CompanyPostRequestBody companyPostRequestBody);
    ResponseEntity<Company> putUpdateCompany(String cnpj, CompanyPutRequestBody companyPutRequestBody);
}
