package com.barreto.stockmanagement.controller.company;

import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPutRequestBody;
import com.barreto.stockmanagement.useCases.company.CompanyUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController implements CompanyEndpoints {
    private final CompanyUseCase companyService;

    @GetMapping("/findByCNPJ")
    public ResponseEntity<Company> getCompanyByCNPJ(@RequestParam String cnpj) {
        return new ResponseEntity<>(companyService.findCompanyByCNPJ(cnpj), HttpStatus.FOUND);
    }

    @GetMapping("/findById")
    public ResponseEntity<Company> getCompanyById(@RequestParam String id) {
        return new ResponseEntity<>(companyService.findCompanyByIdJ(id), HttpStatus.FOUND);
    }

    @PostMapping()
    public ResponseEntity<Company> postNewCompany(@RequestBody @Valid CompanyPostRequestBody companyPostRequestBody) {
        return new ResponseEntity<>(companyService.createNewCompany(companyPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Company> putUpdateCompany(@RequestParam String cnpj, @RequestBody @Valid CompanyPutRequestBody companyPutRequestBody) {
        return new ResponseEntity<>(companyService.updateCompany(cnpj, companyPutRequestBody), HttpStatus.OK);
    }
}
