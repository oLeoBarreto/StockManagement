package com.barreto.stockmanagement.infra.DTOs.Mappers;

import com.barreto.stockmanagement.infra.DTOs.product.ProductPostRequestBody;
import com.barreto.stockmanagement.domains.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public static final ProductMapper INSTANCE = new ProductMapper();
    public Product toProduct(ProductPostRequestBody productPostRequestBody) {
        Product product = new Product();

        product.setName(productPostRequestBody.name());
        product.setDescription(productPostRequestBody.description());
        product.setUnitPrice(productPostRequestBody.unitPrice());
        product.setSupplier(productPostRequestBody.supplier());
        product.setCategory(productPostRequestBody.category());

        return product;
    }
}