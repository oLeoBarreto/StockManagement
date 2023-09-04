package com.barreto.stockmanagement.services.product;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.ProductPutRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductServiceProps {
    public Page<Product> listAllProducts(Pageable pageable);
    public Page<Product> getProductByCategory(Pageable pageable, String category);
    public Page<Product> getProductBySupplier(Pageable pageable, String supplier);
    public Product getProductById(String id);
    public Product saveProduct(ProductPostRequestBody product);
    public Product updateProduct(ProductPutRequestBody product);
    public void deleteProduct(String id);
}
