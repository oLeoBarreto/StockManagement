package com.barreto.stockmanagement.controller;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.ProductPutRequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductControllerProps {
    public ResponseEntity<List<Product>> getProductsLists();
    public ResponseEntity<Product> getProductByCategory(@RequestParam String category);
    public ResponseEntity<Product> getProductBySupplier(@RequestParam String supplier);
    public ResponseEntity<Product> getProductById(@RequestParam String id);
    public ResponseEntity<Product> saveNewProduct(@RequestBody ProductPostRequestBody product);
    public ResponseEntity<Product> updateProduct(@RequestBody ProductPutRequestBody product);
    public ResponseEntity<Void> deleteProduct(@RequestParam String id);
}
