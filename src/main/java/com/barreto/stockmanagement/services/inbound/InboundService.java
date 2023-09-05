package com.barreto.stockmanagement.services.inbound;

import com.barreto.stockmanagement.domains.Inbound;
import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.exceptions.BadRequestException;
import com.barreto.stockmanagement.infra.DTOs.InboundPostRequestBody;
import com.barreto.stockmanagement.infra.database.repository.InboundRepository;
import com.barreto.stockmanagement.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InboundService implements InboundServiceProps{
    private final InboundRepository repository;
    private final ProductService productService;

    public Page<Inbound> listAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Inbound findInboundById(String id) {
        return repository.findById(id).orElseThrow(() ->  new BadRequestException("Inbound ID not found!"));
    }

    public Inbound createInbound(InboundPostRequestBody inboundPostRequestBody) {
        Product product = productService.getProductById(inboundPostRequestBody.getProductId());
        Inbound inbound = new Inbound(product, inboundPostRequestBody.getQuantity());
        product.setStockQuantity(product.getStockQuantity() + inbound.getQuantity());

        return repository.save(inbound);
    }

    public void deleteInbound(String id) {
        Inbound inbound = findInboundById(id);
        Product product = inbound.getProduct();

        product.setStockQuantity(product.getStockQuantity() - inbound.getQuantity());

        repository.delete(inbound);
    }
}
