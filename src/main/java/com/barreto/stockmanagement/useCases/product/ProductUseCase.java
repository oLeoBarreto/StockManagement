package com.barreto.stockmanagement.useCases.product;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPutRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductUseCase {
     Page<Product> listAllProducts(String companyId, Pageable pageable);
     Page<Product> findProductByCategory(Pageable pageable, String category, String companyId);
     Page<Product> findProductBySupplier(Pageable pageable, String supplier, String companyId);
     Product findProductById(String id);
     Product createNewProduct(ProductPostRequestBody product);
     Product updateProduct(ProductPutRequestBody product);
     void deleteProduct(String id);
}
