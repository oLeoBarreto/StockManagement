package com.barreto.stockmanagement.useCases.outbound;

import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.domains.documents.DocumentStatus;
import com.barreto.stockmanagement.domains.documents.Outbound;
import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.outbound.OutboundPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.outbound.OutboundStatusPutRequestBody;
import com.barreto.stockmanagement.infra.database.repository.OutboundRepository;
import com.barreto.stockmanagement.useCases.company.CompanyService;
import com.barreto.stockmanagement.useCases.company.CompanyUseCase;
import com.barreto.stockmanagement.useCases.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OutboundServiceTest {
    @Mock
    private OutboundRepository repository;

    @Mock
    private ProductService productService;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private OutboundService outboundService;

    @BeforeEach
    void setUp() {
        var company = new Company(
                "12.123.123/0001-12",
                "Company test",
                "company@test.com",
                "12345"
        );
        company.setId("companyId");

        Product product = new Product(
                "Produto 1",
                "Descricao do produto de teste",
                new BigDecimal(1),
                "test supplier",
                "test",
                null,
                1F,
                company
        );
        product.id = "productId";

        when(productService.findProductById(anyString())).thenReturn(product);

        Outbound outbound = new Outbound(
                product,
                1F,
                company
        );
        outbound.id = "outboundId";

        PageImpl<Outbound> outboundPage = new PageImpl<>(List.of(outbound));

        when(repository.findAll(any(Pageable.class))).thenReturn(outboundPage);
        when(repository.findOutboundByCompanyId(anyString(), any(Pageable.class))).thenReturn(outboundPage);
        when(repository.findById(anyString())).thenReturn(Optional.of(outbound));
        when(repository.save(any(Outbound.class))).thenReturn(outbound);
        doNothing().when(repository).delete(any(Outbound.class));

        when(companyService.findCompanyById(anyString())).thenReturn(company);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should list all existing outbound")
    void testListAllOutbound() {
        OutboundPostRequestBody outboundPostRequestBody = new OutboundPostRequestBody(
                1F,
                "productId",
                "companyId"
        );
        outboundService.createNewOutbound(outboundPostRequestBody);

        Page<Outbound> outboundPage = outboundService.listAll("companyId", PageRequest.of(0, 10));

        assertFalse(outboundPage.getContent().isEmpty());
    }

    @Test
    @DisplayName("Should find an outbound by ID")
    void testFindOutboundById() {
        OutboundPostRequestBody outboundPostRequestBody = new OutboundPostRequestBody(
                1F,
                "productId",
                "companyId"
        );
        Outbound createdOutbound = outboundService.createNewOutbound(outboundPostRequestBody);

        Outbound foundOutbound = outboundService.findOutboundById(createdOutbound.id);

        assertNotNull(foundOutbound);
        assertEquals(createdOutbound.id, foundOutbound.id);
    }

    @Test
    @DisplayName("Should create a new outbound")
    void testCreateNewOutbound() {
        OutboundPostRequestBody outboundPostRequestBody = new OutboundPostRequestBody(
                1F,
                "productId",
                "companyId"
        );
        Outbound outbound = outboundService.createNewOutbound(outboundPostRequestBody);

        assertNotNull(outbound);
        assertTrue(outbound.getProduct().getStockQuantity() == 1F);
        assertEquals(DocumentStatus.WAITING, outbound.getStatus());
        assertEquals("productId", outbound.getProduct().getId());
    }

    @Test
    @DisplayName("Should be able to change the status of a outbound document")
    void testChangeDocStatus() {
        OutboundPostRequestBody outboundPostRequestBody = new OutboundPostRequestBody(
                1F,
                "productId",
                "companyId"
        );
        Outbound outbound = outboundService.createNewOutbound(outboundPostRequestBody);

        var outboundStatusPutRequestBody = new OutboundStatusPutRequestBody(outbound.getId(), DocumentStatus.COMPLETED);

        assertDoesNotThrow(() -> outboundService.updateOutboundStatus(outboundStatusPutRequestBody));
        assertTrue(outbound.getProduct().getStockQuantity() == 0F);
    }

    @Test
    @DisplayName("Should delete a existing outbound")
    void testDeleteOutbound() {
        OutboundPostRequestBody outboundPostRequestBody = new OutboundPostRequestBody(
                1F,
                "productId",
                "companyId"
        );
        Outbound outbound = outboundService.createNewOutbound(outboundPostRequestBody);

        assertDoesNotThrow(() -> outboundService.deleteOutbound(outbound.id));
        assertTrue(repository.findAll().isEmpty());
    }
}