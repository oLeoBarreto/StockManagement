package com.barreto.stockmanagement.controller.inbounds;

import com.barreto.stockmanagement.domains.Inbound;
import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.inbound.InboundPostRequestBody;
import com.barreto.stockmanagement.useCases.inbound.InboundService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class InboundControllerTest {
    @Mock
    private InboundService inboundService;

    @InjectMocks
    private InboundController inboundController;

    @BeforeEach
    void setUp() {
        Product product = new Product(
                "Produto 1",
                "Descricao do produto de teste",
                new BigDecimal(1),
                "test supplier",
                "test",
                0F
        );
        product.id = "productId";

        Inbound inbound = new Inbound(
                product,
                1F
        );
        inbound.id = "inboundId";

        PageImpl<Inbound> inboundPage = new PageImpl<>(List.of(inbound));

        when(inboundService.createInbound(any(InboundPostRequestBody.class))).thenReturn(inbound);
        when(inboundService.listAll(any(Pageable.class))).thenReturn(inboundPage);
        when(inboundService.findInboundById(anyString())).thenReturn(inbound);
        doNothing().when(inboundService).deleteInbound(anyString());

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should get a page of existing inbounds")
    void testGetInboundList() {
        ResponseEntity<Page<Inbound>> inbound = inboundController.getInboundList(PageRequest.of(0, 10));

        assertNotNull(inbound.getBody());

        assertEquals(HttpStatus.OK, inbound.getStatusCode());
    }

    @Test
    @DisplayName("Should get an existing inbounds by ID")
    void testGetInboundById() {
        ResponseEntity<Inbound> inbound = inboundController.getInboundById("inboundId");

        assertNotNull(inbound.getBody());
        assertEquals("inboundId", inbound.getBody().id);
        assertEquals(HttpStatus.FOUND, inbound.getStatusCode());
    }

    @Test
    @DisplayName("Should post a new inbound")
    void testPostNewInbound() {
        InboundPostRequestBody inboundPostRequestBody = new InboundPostRequestBody(
                1F,
                "productId"
        );

        ResponseEntity<Inbound> inbound = inboundController.postNewInbound(inboundPostRequestBody);

        assertNotNull(inbound.getBody());
        assertEquals("Produto 1", inbound.getBody().getProduct().getName());
        assertEquals(HttpStatus.CREATED, inbound.getStatusCode());
    }

    @Test
    @DisplayName("Should delete an existing inbound")
    void testDeleteInbound() {
        ResponseEntity<Void> inbound = inboundController.deleteInbound("inboundId");

        assertDoesNotThrow(() -> inboundController.deleteInbound("inboundId"));
        assertEquals(HttpStatus.NO_CONTENT, inbound.getStatusCode());
    }
}