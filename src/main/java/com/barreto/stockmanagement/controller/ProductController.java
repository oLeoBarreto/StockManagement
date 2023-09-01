package com.barreto.stockmanagement.controller;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.ProductPutRequestBody;
import com.barreto.stockmanagement.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController implements ProductControllerProps{
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<List<Product>> getProductsLists() {
        return new ResponseEntity<>(productService.listAllProducts(), HttpStatus.OK);
    }

    @GetMapping("findByCategory")
    public ResponseEntity<Product> getProductByCategory(@RequestParam String category) {
        return new ResponseEntity<>(productService.getProductByCategory(category), HttpStatus.FOUND);
    }

    @GetMapping("findBySupplier")
    public ResponseEntity<Product> getProductBySupplier(@RequestParam String supplier) {
        return new ResponseEntity<>(productService.getProductBySupplier(supplier), HttpStatus.FOUND);
    }

    @GetMapping("findById")
    public ResponseEntity<Product> getProductById(@RequestParam String id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.FOUND);
    }

    @PostMapping()
    public ResponseEntity<Product> saveNewProduct(ProductPostRequestBody product) {
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Product> updateProduct(ProductPutRequestBody product) {
        return new ResponseEntity<>(productService.updateProduct(product), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteProduct(String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
