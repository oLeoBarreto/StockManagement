package com.barreto.stockmanagement.services.product;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.Mappers.ProductMapper;
import com.barreto.stockmanagement.infra.DTOs.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.ProductPutRequestBody;
import com.barreto.stockmanagement.infra.database.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceProps {

    private final ProductRepository repository;

    public List<Product> listAllProducts() {
        return repository.findAll();
    }

    public Product getProductByCategory(String category) {
        return repository.findByCategory(category);
    }

    public Product getProductBySupplier(String supplier) {
        return repository.findBySupplier(supplier);
    }

    public Product getProductById(String id) {
        return repository.findById(id).orElseThrow();
    }

    public Product saveProduct(ProductPostRequestBody product) {
        return repository.save(ProductMapper.INSTANCE.toProduct(product));
    }

    public Product updateProduct(ProductPutRequestBody product) {
        Product findProduct = getProductById(product.getId());
        return repository.save(findProduct);
    }

    public void deleteProduct(String id) {
        repository.delete(getProductById(id));
    }
}
