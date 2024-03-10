package com.barreto.stockmanagement.controller.companies;

import com.barreto.stockmanagement.controller.company.CompanyController;
import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPutRequestBody;
import com.barreto.stockmanagement.useCases.company.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CompanyControllerTest {
    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    @BeforeEach
    void setUp() {
        var company =  new Company(
                "12.123.123/0001-12",
                "Company test",
                "company@test.com",
                "12345"
        );
        company.setId("companyId");

        when(companyService.createNewCompany(any(CompanyPostRequestBody.class))).thenReturn(company);
        when(companyService.updateCompany(anyString(), any(CompanyPutRequestBody.class))).thenReturn(company);
        when(companyService.findCompanyByIdJ(anyString())).thenReturn(company);
        when(companyService.findCompanyByCNPJ(anyString())).thenReturn(company);
        doNothing().when(companyService).deleteExistingCompany(anyString());

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should be able to get a company by CNPJ")
    void testGetCompanyByCNPJ() {
        var companyResponse = companyController.getCompanyByCNPJ("12.123.123/0001-12");

        assertNotNull(companyResponse.getBody());
        assertEquals(HttpStatus.FOUND, companyResponse.getStatusCode());
    }

    @Test
    @DisplayName("Should be able to get a company by Id")
    void testGetCompanyById() {
        var companyResponse = companyController.getCompanyById("companyId");

        assertNotNull(companyResponse.getBody());
        assertEquals(HttpStatus.FOUND, companyResponse.getStatusCode());
    }

    @Test
    @DisplayName("Should be able to post new company")
    void testPostNewCompany() {
        var companyPostRequestBody = new CompanyPostRequestBody("12.123.123/0001-12",
                "Company test",
                "company@test.com",
                "12345");

        var companyResponse = companyController.postNewCompany(companyPostRequestBody);

        assertNotNull(companyResponse.getBody());
        assertEquals(HttpStatus.CREATED, companyResponse.getStatusCode());
    }

    @Test
    @DisplayName("Should be able to put and update a company")
    void testPutUpdateCompany() {
        var companyPutRequestBody = new CompanyPutRequestBody("Company test",
                "company@test.com",
                "12345");
        var companyResponse = companyController.putUpdateCompany("12.123.123/0001-12", companyPutRequestBody);

        assertNotNull(companyResponse.getBody());
        assertEquals(HttpStatus.OK, companyResponse.getStatusCode());
    }

    @Test
    @DisplayName("Should be able to delete a company")
    void testDeleteCompany() {
        var companyResponse = companyController.deleteCompany("12.123.123/0001-12");

        assertDoesNotThrow(() -> companyController.deleteCompany("12.123.123/0001-12"));
        assertEquals(HttpStatus.NO_CONTENT, companyResponse.getStatusCode());
    }

}
