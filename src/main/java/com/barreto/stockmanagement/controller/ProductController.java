package com.barreto.stockmanagement.controller;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.ProductPutRequestBody;
import com.barreto.stockmanagement.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController implements ProductControllerProps{
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<Page<Product>> getProductsLists(Pageable pageable) {
        return new ResponseEntity<>(productService.listAllProducts(pageable), HttpStatus.OK);
    }

    @GetMapping("findByCategory")
    public ResponseEntity<Page<Product>> getProductByCategory(Pageable pageable, @RequestParam String category) {
        return new ResponseEntity<>(productService.getProductByCategory(pageable, category), HttpStatus.FOUND);
    }

    @GetMapping("findBySupplier")
    public ResponseEntity<Page<Product>>  getProductBySupplier(Pageable pageable, @RequestParam String supplier) {
        return new ResponseEntity<>(productService.getProductBySupplier(pageable, supplier), HttpStatus.FOUND);
    }

    @GetMapping("findById")
    public ResponseEntity<Product> getProductById(@RequestParam String id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.FOUND);
    }

    @PostMapping()
    public ResponseEntity<Product> saveNewProduct(@RequestBody ProductPostRequestBody product) {
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Product> updateProduct(@RequestBody ProductPutRequestBody product) {
        return new ResponseEntity<>(productService.updateProduct(product), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteProduct(@RequestParam String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
