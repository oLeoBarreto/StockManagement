package com.barreto.stockmanagement.services.product;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.ProductPutRequestBody;

import java.util.List;

public interface ProductServiceProps {
    public List<Product> listAllProducts();
    public Product getProductByCategory(String category);
    public Product getProductBySupplier(String supplier);
    public Product getProductById(String id);
    public Product saveProduct(ProductPostRequestBody product);
    public Product updateProduct(ProductPutRequestBody product);
    public void deleteProduct(String id);
}
