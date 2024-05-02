package com.barreto.stockmanagement.controller.products;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPutRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProductEndpoints {
     ResponseEntity<Page<Product>> getProductsLists(Pageable pageable, String companyId);
     ResponseEntity<Page<Product>> getProductByCategory(Pageable pageable, String category, String companyId);
     ResponseEntity<Page<Product>> getProductBySupplier(Pageable pageable, String supplier, String companyId);
     ResponseEntity<Product> getProductById(String id);
     ResponseEntity<byte[]> getProductImage(String productId);
     ResponseEntity<Product> postNewProduct(ProductPostRequestBody product);
     ResponseEntity<Product> postProductImage(String productId, MultipartFile image);
     ResponseEntity<Product> putProduct(ProductPutRequestBody product);
     ResponseEntity<Void> deleteProduct(String id);
     ResponseEntity<Void> deleteProductImage(String productId);
}
