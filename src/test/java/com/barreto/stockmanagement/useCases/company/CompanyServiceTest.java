package com.barreto.stockmanagement.useCases.company;

import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPutRequestBody;
import com.barreto.stockmanagement.infra.database.repository.CompanyRepository;
import com.barreto.stockmanagement.infra.exceptions.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    private CompanyRepository repository;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        var company =  new Company(
                "12.123.123/0001-12",
                "Company test",
                "company@test.com",
                "12345"
        );
        company.setId("companyId");

        when(repository.findById(anyString())).thenReturn(Optional.of(company));
        when(repository.findByCnpj(anyString())).thenReturn(Optional.of(company));
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(company));
        when(repository.save(any(Company.class))).thenReturn(company);
        doNothing().when(repository).delete(any(Company.class));

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should be able to create a new company")
    void testCreateNewCompany() {
        var companyPostRequestBody = new CompanyPostRequestBody(
                "12.123.123/0001-12",
                "Company test",
                "company@test.com",
                "12345"
        );

        var company = companyService.createNewCompany(companyPostRequestBody);

        assertNotNull(company);
        assertEquals("companyId", company.getId());
    }

    @Test
    @DisplayName("Should not be able to create a company with a used CNPJ")
    void testDoesNotCreateCompanyWithUsedCNPJ() {
        var companyPostRequestBody1 = new CompanyPostRequestBody(
                "12.123.123/0001-12",
                "Company test",
                "company@test.com",
                "12345"
        );
        companyService.createNewCompany(companyPostRequestBody1);

        var companyPostRequestBody2 = new CompanyPostRequestBody(
                "12.123.123/0001-12",
                "Company test 2",
                "company2@test.com",
                "12345"
        );

        assertThrows(
                BadRequestException.class,
                () -> companyService.createNewCompany(companyPostRequestBody2),
                "Already exists a company with this CNPJ!");
    }

    @Test
    @DisplayName("Should not be able to create a company with a used email")
    void testDoesNotCreateCompanyWithUsedEmail() {
        var companyPostRequestBody1 = new CompanyPostRequestBody(
                "12.123.123/0001-12",
                "Company test",
                "company@test.com",
                "12345"
        );
        companyService.createNewCompany(companyPostRequestBody1);

        var companyPostRequestBody2 = new CompanyPostRequestBody(
                "12.123.123/0001-13",
                "Company test 2",
                "company@test.com",
                "12345"
        );

        assertThrows(
                BadRequestException.class,
                () -> companyService.createNewCompany(companyPostRequestBody2),
                "Already exists a company with this email!");
    }

    @Test
    @DisplayName("Should be able to update company data")
    void testUpdateCompanyData() {
        var companyPutRequestBody = new CompanyPutRequestBody(
                "Company test 2",
                "company@test.com",
                "12345"
        );

        var company = companyService.updateCompany("12.123.123/0001-12", companyPutRequestBody);

        assertNotNull(company);
        assertEquals("Company test 2", company.getName());
    }

    @Test
    @DisplayName("Should not be able to update a not existing company data")
    void testUpdateNotExistingCompanyData() {
        var companyPutRequestBody = new CompanyPutRequestBody(
                "Company test 2",
                "company@test.com",
                "12345"
        );

        assertThrows(
                BadRequestException.class,
                () -> companyService.updateCompany("12.123.123/0001-11", companyPutRequestBody),
                "Not exists a company with that CNPJ!");
    }

    @Test
    @DisplayName("Should be able to delete a company")
    void testDeleteCompany() {
        assertDoesNotThrow(() -> companyService.deleteExistingCompany("12.123.123/0001-12"));
    }
}
