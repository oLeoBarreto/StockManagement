package com.barreto.stockmanagement.useCases.product;

import com.barreto.stockmanagement.domains.Product;
import org.springframework.web.multipart.MultipartFile;

public interface ProductImageUseCase {
    Product saveProductImage(String productId, MultipartFile image);
    byte[] findProductImage(String productId);
    void deleteProductImage(String productId);
}
