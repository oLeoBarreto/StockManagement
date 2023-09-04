package com.barreto.stockmanagement.controller;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.ProductPutRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductControllerProps {
    public ResponseEntity<Page<Product>> getProductsLists(Pageable pageable);
    public ResponseEntity<Page<Product>> getProductByCategory(Pageable pageable, @RequestParam String category);
    public ResponseEntity<Page<Product>> getProductBySupplier(Pageable pageable, @RequestParam String supplier);
    public ResponseEntity<Product> getProductById(@RequestParam String id);
    public ResponseEntity<Product> saveNewProduct(@RequestBody ProductPostRequestBody product);
    public ResponseEntity<Product> updateProduct(@RequestBody ProductPutRequestBody product);
    public ResponseEntity<Void> deleteProduct(@RequestParam String id);
}
