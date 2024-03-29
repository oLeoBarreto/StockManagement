package com.barreto.stockmanagement.useCases.inbound;

import com.barreto.stockmanagement.domains.Inbound;
import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.inbound.InboundPostRequestBody;
import com.barreto.stockmanagement.infra.database.repository.InboundRepository;
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

    @InjectMocks
    private InboundService inboundService;

    @BeforeEach
    void setUp() {
        Product product = new Product(
                "Produto 1",
                "Descricao do produto de teste",
                new BigDecimal(1),
                "test supplier",
                "test",
                null,
                0F
        );
        product.id = "productId";

        when(productService.findProductById(anyString())).thenReturn(product);

        Inbound inbound = new Inbound(
                product,
                1F
        );
        inbound.id = "inboundId";

        PageImpl<Inbound> inboundPage = new PageImpl<>(List.of(inbound));

        when(repository.findAll(any(Pageable.class))).thenReturn(inboundPage);
        when(repository.findById(anyString())).thenReturn(Optional.of(inbound));
        when(repository.save(any(Inbound.class))).thenReturn(inbound);
        doNothing().when(repository).delete(any(Inbound.class));

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should list all existing inbounds")
    void testListAllInbounds() {
        InboundPostRequestBody inboundPostRequestBody = new InboundPostRequestBody(1F, "productId");
        inboundService.createInbound(inboundPostRequestBody);

        Page<Inbound> inboundPage = inboundService.listAll(PageRequest.of(0, 10));

        assertFalse(inboundPage.getContent().isEmpty());
    }

    @Test
    @DisplayName("Should find an existing inbound by ID")
    void testFindInboundById() {
        InboundPostRequestBody inboundPostRequestBody = new InboundPostRequestBody(1F, "productId");
        Inbound createdInbound = inboundService.createInbound(inboundPostRequestBody);

        Inbound foundInbound = inboundService.findInboundById(createdInbound.id);

        assertNotNull(foundInbound);
        assertEquals(createdInbound.id, foundInbound.id);
    }

    @Test
    @DisplayName("Should create a new Inbound")
    void testCreateNewInbound() {
        InboundPostRequestBody inboundPostRequestBody = new InboundPostRequestBody(1F, "productId");
        Inbound inbound = inboundService.createInbound(inboundPostRequestBody);

        assertNotNull(inbound);
        assertTrue(inbound.getProduct().getStockQuantity() == 1F);
    }

    @Test
    @DisplayName("Should delete an existing Inbound")
    void testDeleteInbound() {
        InboundPostRequestBody inboundPostRequestBody = new InboundPostRequestBody(1F, "productId");
        Inbound inbound = inboundService.createInbound(inboundPostRequestBody);

        inboundService.deleteInbound(inbound.id);

        assertTrue(repository.findAll().isEmpty());
        assertTrue(inbound.getProduct().getStockQuantity() == 0F);
    }
}