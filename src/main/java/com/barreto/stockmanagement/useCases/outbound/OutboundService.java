package com.barreto.stockmanagement.useCases.outbound;

import com.barreto.stockmanagement.domains.documents.DocumentStatus;
import com.barreto.stockmanagement.domains.documents.Outbound;
import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.outbound.OutboundStatusPutRequestBody;
import com.barreto.stockmanagement.infra.exceptions.BadRequestException;
import com.barreto.stockmanagement.infra.DTOs.outbound.OutboundPostRequestBody;
import com.barreto.stockmanagement.infra.database.repository.OutboundRepository;
import com.barreto.stockmanagement.useCases.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutboundService implements OutboundUseCase {
    private final OutboundRepository repository;
    private final ProductService productService;

    public Page<Outbound> listAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Outbound findOutboundById(String id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("Outbound ID not found!"));
    }

    public Outbound createNewOutbound(OutboundPostRequestBody outboundPostRequestBody) {
        Product product = productService.findProductById(outboundPostRequestBody.productId());
        Outbound outbound = new Outbound(product, outboundPostRequestBody.quantity());

        return repository.save(outbound);
    }

    public Outbound updateOutboundStatus(OutboundStatusPutRequestBody outboundStatusPutRequestBody) {
        Outbound outbound = findOutboundById(outboundStatusPutRequestBody.outboundId());

        if (outbound.getStatus() == DocumentStatus.COMPLETED) {
            throw new BadRequestException("This outbound is completed! Can't change the status.");
        }

        if (outbound.getStatus() == outboundStatusPutRequestBody.status()) {
            throw new BadRequestException("This outbound already in this status!");
        }

        outbound.setStatus(outboundStatusPutRequestBody.status());
        outbound.getProduct().setStockQuantity(outbound.getProduct().getStockQuantity() - outbound.getQuantity());

        return repository.save(outbound);
    }

    public void deleteOutbound(String id) {
        Outbound outbound = findOutboundById(id);
        Product product = outbound.getProduct();

        product.setStockQuantity(product.getStockQuantity() + outbound.getQuantity());

        repository.delete(outbound);
    }
}
