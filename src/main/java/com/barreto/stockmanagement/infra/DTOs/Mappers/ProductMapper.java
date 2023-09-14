package com.barreto.stockmanagement.infra.DTOs.Mappers;

import com.barreto.stockmanagement.infra.DTOs.product.ProductPostRequestBody;
import com.barreto.stockmanagement.domains.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public static final ProductMapper INSTANCE = new ProductMapper();
    public Product toProduct(ProductPostRequestBody productPostRequestBody) {
        Product product = new Product();

        product.setName(productPostRequestBody.getName());
        product.setDescription(productPostRequestBody.getDescription());
        product.setUnitPrice(productPostRequestBody.getUnitPrice());
        product.setSupplier(productPostRequestBody.getSupplier());
        product.setCategory(productPostRequestBody.getCategory());

        return product;
    }
}