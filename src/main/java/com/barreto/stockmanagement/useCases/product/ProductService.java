package com.barreto.stockmanagement.useCases.product;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.config.utils.FileStorage;
import com.barreto.stockmanagement.infra.exceptions.BadRequestException;
import com.barreto.stockmanagement.infra.DTOs.Mappers.ProductMapper;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPutRequestBody;
import com.barreto.stockmanagement.infra.database.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepository repository;
    private final FileStorage fileStorage;

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

    public Product saveProductImage(String productId, MultipartFile image) {
        String imageContentType = image.getContentType();
        List<String> allowedContentType = Arrays.asList(
                MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE
        );

        if (!allowedContentType.contains(imageContentType)) {
            throw new BadRequestException("Image file is not supported! Supported: JPEG, PNG");
        }

        Product product = findProductById(productId);

        String fileName = "image-" + product.id + ".jpeg";

        Path uploadPath = Path.of(fileStorage.getUploadDir());
        Path filePath = uploadPath.resolve(fileName);

        try (InputStream inputStream = image.getInputStream()){
            if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
            }

            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new BadRequestException("Image file is not supported!");
        }

        product.setImage(fileName);
        return repository.save(product);
    }

    public byte[] findProductImage(String productId) {
        Product product = findProductById(productId);

        Path imagePath = Path.of(fileStorage.getUploadDir(), product.getImage());

        try {
            if (Files.exists(imagePath)) {
                return Files.readAllBytes(imagePath);
            }else return null;
        } catch (IOException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    public void deleteProduct(String id) {
        repository.delete(findProductById(id));
    }

    public void deleteProductImage(String productId) {
        Product product = findProductById(productId);

        Path imagePath = Path.of(fileStorage.getUploadDir(), product.getImage());

        try {
            if (Files.exists(imagePath)) {
                Files.delete(imagePath);
            }
        } catch (IOException ex) {
            throw new BadRequestException(ex.getMessage());
        }

        product.setImage(null);
        repository.save(product);
    }
}
