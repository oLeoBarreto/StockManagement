package com.barreto.stockmanagement.controller.company;

import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.company.CompanyPutRequestBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserLoginResponseBody;
import com.barreto.stockmanagement.infra.exceptions.BadRequestExceptionDetail;
import com.barreto.stockmanagement.useCases.company.CompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
@Tag(name = "Company", description = "Company operations endpoints")
public class CompanyController implements CompanyEndpoints {
    private final CompanyUseCase companyService;

    @GetMapping("/findByCNPJ")
    @Operation(summary = "Find a existing company by the CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Success found response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Company.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Company> getCompanyByCNPJ(@RequestParam String cnpj) {
        return new ResponseEntity<>(companyService.findCompanyByCNPJ(cnpj), HttpStatus.FOUND);
    }

    @GetMapping("/findById")
    @Operation(summary = "Find a existing company by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Success found response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Company.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Company> getCompanyById(@RequestParam String id) {
        return new ResponseEntity<>(companyService.findCompanyById(id), HttpStatus.FOUND);
    }

    @PostMapping()
    @Operation(summary = "Create a new company account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success create response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Company.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Company> postNewCompany(@RequestBody @Valid CompanyPostRequestBody companyPostRequestBody) {
        return new ResponseEntity<>(companyService.createNewCompany(companyPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping()
    @Operation(summary = "Update a existing company data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success update response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Company.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Company> putUpdateCompany(@RequestParam String cnpj, @RequestBody @Valid CompanyPutRequestBody companyPutRequestBody) {
        return new ResponseEntity<>(companyService.updateCompany(cnpj, companyPutRequestBody), HttpStatus.OK);
    }
}
