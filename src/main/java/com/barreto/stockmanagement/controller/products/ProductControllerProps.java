package com.barreto.stockmanagement.controller.products;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.ProductPutRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ProductControllerProps {
     ResponseEntity<Page<Product>> getProductsLists(Pageable pageable);
     ResponseEntity<Page<Product>> getProductByCategory(Pageable pageable, String category);
     ResponseEntity<Page<Product>> getProductBySupplier(Pageable pageable, String supplier);
     ResponseEntity<Product> getProductById(String id);
     ResponseEntity<Product> postNewProduct(ProductPostRequestBody product);
     ResponseEntity<Product> putProduct(ProductPutRequestBody product);
     ResponseEntity<Void> deleteProduct(String id);
}
