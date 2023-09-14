package com.barreto.stockmanagement.useCases.outbound;

import com.barreto.stockmanagement.domains.Outbound;
import com.barreto.stockmanagement.domains.Product;
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
        Product product = productService.findProductById(outboundPostRequestBody.getProductId());
        Outbound outbound = new Outbound(product, outboundPostRequestBody.getQuantity());

        product.setStockQuantity(product.getStockQuantity() - outboundPostRequestBody.getQuantity());

        return repository.save(outbound);
    }

    public void deleteOutbound(String id) {
        Outbound outbound = findOutboundById(id);
        Product product = outbound.getProduct();

        product.setStockQuantity(product.getStockQuantity() + outbound.getQuantity());

        repository.delete(outbound);
    }
}
