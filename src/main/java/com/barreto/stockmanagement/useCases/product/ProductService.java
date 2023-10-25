package com.barreto.stockmanagement.useCases.product;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.exceptions.BadRequestException;
import com.barreto.stockmanagement.infra.DTOs.Mappers.ProductMapper;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPutRequestBody;
import com.barreto.stockmanagement.infra.database.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepository repository;

    public Page<Product> listAllProducts(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Product> findProductByCategory(Pageable pageable, String category) {
        return repository.findByCategory(pageable, category);
    }

    public Page<Product> findProductBySupplier(Pageable pageable, String supplier) {
        return repository.findBySupplier(pageable, supplier);
    }

    public Product findProductById(String id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("Product ID not found!"));
    }

    @Transactional
    public Product createNewProduct(ProductPostRequestBody product) {
        return repository.save(ProductMapper.INSTANCE.toProduct(product));
    }

    public Product updateProduct(ProductPutRequestBody product) {
        Product findProduct = findProductById(product.id());
        findProduct.setName(product.name());
        findProduct.setDescription(product.description());
        findProduct.setUnitPrice(product.unitPrice());
        findProduct.setSupplier(product.supplier());
        findProduct.setCategory(product.category());

        return repository.save(findProduct);
    }

    public void deleteProduct(String id) {
        repository.delete(findProductById(id));
    }
}
