package com.barreto.stockmanagement.controller.products;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPutRequestBody;
import com.barreto.stockmanagement.useCases.product.ProductImageUseCase;
import com.barreto.stockmanagement.useCases.product.ProductUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController implements ProductEndpoints {
    private final ProductUseCase productService;
    private final ProductImageUseCase productImageService;

    @GetMapping()
    public ResponseEntity<Page<Product>> getProductsLists(Pageable pageable) {
        return new ResponseEntity<>(productService.listAllProducts(pageable), HttpStatus.OK);
    }

    @GetMapping("/findByCategory")
    public ResponseEntity<Page<Product>> getProductByCategory(Pageable pageable, @RequestParam String category) {
        return new ResponseEntity<>(productService.findProductByCategory(pageable, category), HttpStatus.FOUND);
    }

    @GetMapping("/findBySupplier")
    public ResponseEntity<Page<Product>> getProductBySupplier(Pageable pageable, @RequestParam String supplier) {
        return new ResponseEntity<>(productService.findProductBySupplier(pageable, supplier), HttpStatus.FOUND);
    }

    @GetMapping("/findById")
    public ResponseEntity<Product> getProductById(@RequestParam String id) {
        return new ResponseEntity<>(productService.findProductById(id), HttpStatus.FOUND);
    }

    @GetMapping("/image/download")
    public ResponseEntity<byte[]> getProductImage(@RequestParam String productId) {
        return ResponseEntity.status(HttpStatus.FOUND).contentType(MediaType.valueOf("image/png")).body(productImageService.findProductImage(productId));
    }

    @PostMapping()
    public ResponseEntity<Product> postNewProduct(@RequestBody @Valid ProductPostRequestBody product) {
        return new ResponseEntity<>(productService.createNewProduct(product), HttpStatus.CREATED);
    }

    @PostMapping("/image/upload")
    public ResponseEntity<Product> postProductImage(@RequestParam String productId,@RequestParam MultipartFile imageFile) {
        return new ResponseEntity<>(productImageService.saveProductImage(productId, imageFile), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Product> putProduct(@RequestBody @Valid ProductPutRequestBody product) {
        return new ResponseEntity<>(productService.updateProduct(product), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteProduct(@RequestParam String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/image")
    public ResponseEntity<Void> deleteProductImage(String productId) {
        productImageService.deleteProductImage(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
