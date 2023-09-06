package com.barreto.stockmanagement.services.product;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.ProductPutRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductServiceProps {
     Page<Product> listAllProducts(Pageable pageable);
     Page<Product> findProductByCategory(Pageable pageable, String category);
     Page<Product> findProductBySupplier(Pageable pageable, String supplier);
     Product findProductById(String id);
     Product createNewProduct(ProductPostRequestBody product);
     Product updateProduct(ProductPutRequestBody product);
     void deleteProduct(String id);
}
