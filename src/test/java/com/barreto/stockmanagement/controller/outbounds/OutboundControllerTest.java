package com.barreto.stockmanagement.controller.outbounds;

import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.domains.documents.DocumentStatus;
import com.barreto.stockmanagement.domains.documents.Outbound;
import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.outbound.OutboundPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.outbound.OutboundStatusPutRequestBody;
import com.barreto.stockmanagement.useCases.outbound.OutboundService;
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
class OutboundControllerTest {

    @Mock
    private OutboundService outboundService;

    @InjectMocks
    private OutboundController outboundController;

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

        Outbound outbound = new Outbound(
                product,
                1F,
                company
        );

        PageImpl<Outbound> outboundPage = new PageImpl<>(List.of(outbound));

        when(outboundService.createNewOutbound(any(OutboundPostRequestBody.class))).thenReturn(outbound);
        when(outboundService.listAll(anyString(), any(Pageable.class))).thenReturn(outboundPage);
        when(outboundService.findOutboundById(anyString())).thenReturn(outbound);
        when(outboundService.updateOutboundStatus(any(OutboundStatusPutRequestBody.class))).thenReturn(outbound);
        doNothing().when(outboundService).deleteOutbound(anyString());

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should get a page of existing outbounds")
    void testGetOutboundList() {
        ResponseEntity<Page<Outbound>> outbound = outboundController.getOutboundList("companyId", PageRequest.of(0, 10));

        assertNotNull(outbound.getBody());
        assertEquals(HttpStatus.OK, outbound.getStatusCode());
        assertEquals("Produto 1", outbound.getBody().toList().get(0).getProduct().getName());
    }

    @Test
    @DisplayName("Should get an existing outbound by ID")
    void testGetOutboundById() {
        ResponseEntity<Outbound> outbound = outboundController.getOutboundById("outboundId");

        assertNotNull(outbound.getBody());
        assertEquals(HttpStatus.FOUND, outbound.getStatusCode());
    }

    @Test
    @DisplayName("Should post a new outbound")
    void testPostNewOutbound() {
        OutboundPostRequestBody outboundPostRequestBody = new OutboundPostRequestBody(
                1F,
                "productId",
                "companyId"
        );

        ResponseEntity<Outbound> outbound = outboundController.postNewOutbound(outboundPostRequestBody);

        assertNotNull(outbound.getBody());
        assertEquals(HttpStatus.CREATED, outbound.getStatusCode());
    }

    @Test
    @DisplayName("Should be able to put the outbound status")
    void testPutOutboundStatus() {
        var outboundStatusPutRequestBody = new OutboundStatusPutRequestBody("outboundId", DocumentStatus.COMPLETED);
        var outbound = outboundController.putInboundStatus(outboundStatusPutRequestBody);

        assertNotNull(outbound.getBody());
        assertEquals(HttpStatus.OK, outbound.getStatusCode());
    }

    @Test
    @DisplayName("Should delete an existing outbound")
    void testDeleteOutbound() {
        ResponseEntity<Void> outbound = outboundController.deleteOutbound("outboundId");

        assertDoesNotThrow(() -> outboundController.deleteOutbound("outboundId"));
        assertEquals(HttpStatus.NO_CONTENT, outbound.getStatusCode());
    }
}