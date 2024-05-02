package com.barreto.stockmanagement.useCases.inbound;

import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.domains.documents.DocumentStatus;
import com.barreto.stockmanagement.domains.documents.Inbound;
import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.inbound.InboundPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.inbound.InboundStatusPutRequestBody;
import com.barreto.stockmanagement.infra.database.repository.InboundRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class InboundServiceTest {
    @Mock
    private InboundRepository repository;

    @Mock
    private ProductService productService;

    @Mock
    private CompanyUseCase companyService;

    @InjectMocks
    private InboundService inboundService;

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
                0F,
                company
        );
        product.id = "productId";

        when(productService.findProductById(anyString())).thenReturn(product);

        Inbound inbound = new Inbound(
                product,
                1F,
                company
        );
        inbound.id = "inboundId";

        PageImpl<Inbound> inboundPage = new PageImpl<>(List.of(inbound));

        when(repository.findAllByCompanyId(anyString(), any(Pageable.class))).thenReturn(inboundPage);
        when(repository.findById(anyString())).thenReturn(Optional.of(inbound));
        when(repository.save(any(Inbound.class))).thenReturn(inbound);
        doNothing().when(repository).delete(any(Inbound.class));
        when(companyService.findCompanyById(anyString())).thenReturn(company);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should list all existing inbounds")
    void testListAllInbounds() {
        InboundPostRequestBody inboundPostRequestBody = new InboundPostRequestBody(1F, "productId", "companyId");
        inboundService.createInbound(inboundPostRequestBody);

        Page<Inbound> inboundPage = inboundService.listAll(PageRequest.of(0, 10), "companyId");

        assertFalse(inboundPage.getContent().isEmpty());
    }

    @Test
    @DisplayName("Should find an existing inbound by ID")
    void testFindInboundById() {
        InboundPostRequestBody inboundPostRequestBody = new InboundPostRequestBody(1F, "productId", "companyId");
        Inbound createdInbound = inboundService.createInbound(inboundPostRequestBody);

        Inbound foundInbound = inboundService.findInboundById(createdInbound.id);

        assertNotNull(foundInbound);
        assertEquals(createdInbound.id, foundInbound.id);
    }

    @Test
    @DisplayName("Should create a new Inbound")
    void testCreateNewInbound() {
        InboundPostRequestBody inboundPostRequestBody = new InboundPostRequestBody(1F, "productId", "companyId");
        Inbound inbound = inboundService.createInbound(inboundPostRequestBody);

        assertNotNull(inbound);
        assertTrue(inbound.getProduct().getStockQuantity() == 0F);
        assertEquals("productId", inbound.getProduct().getId());
        assertEquals(DocumentStatus.WAITING, inbound.getStatus());
    }

    @Test
    @DisplayName("Should be able to change the status of a inbound document")
    void testChangeDocStatus() {
        InboundPostRequestBody inboundPostRequestBody = new InboundPostRequestBody(1F, "productId", "companyId");
        Inbound inbound = inboundService.createInbound(inboundPostRequestBody);

        var docStatus = new InboundStatusPutRequestBody(inbound.getId(), DocumentStatus.COMPLETED);

        assertDoesNotThrow(() -> inboundService.updateInboundStatus(docStatus));
        assertTrue(inbound.getProduct().getStockQuantity() == 1F);
    }

    @Test
    @DisplayName("Should delete an existing Inbound")
    void testDeleteInbound() {
        InboundPostRequestBody inboundPostRequestBody = new InboundPostRequestBody(1F, "productId", "companyId");
        Inbound inbound = inboundService.createInbound(inboundPostRequestBody);

        assertDoesNotThrow(() -> inboundService.deleteInbound(inbound.id));
        assertTrue(repository.findAll().isEmpty());
    }
}