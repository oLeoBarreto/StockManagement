package com.barreto.stockmanagement.useCases.product;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.config.utils.FileStorage;
import com.barreto.stockmanagement.infra.database.repository.ProductRepository;
import com.barreto.stockmanagement.infra.exceptions.BadRequestException;
import com.barreto.stockmanagement.infra.exceptions.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
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
public class ProductImageService implements ProductImageUseCase {

    private final ProductService productService;
    private final ProductRepository repository;
    private final FileStorage fileStorage;

    public Product saveProductImage(String productId, MultipartFile image) {
        String imageContentType = image.getContentType();
        List<String> allowedContentType = Arrays.asList(
                MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE
        );

        if (!allowedContentType.contains(imageContentType)) {
            throw new BadRequestException("Image file is not supported! Supported: JPEG, PNG");
        }

        Product product = productService.findProductById(productId);

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
        Product product = productService.findProductById(productId);

        if (product.getImage() == null) {
            throw new ImageNotFoundException("This product not contain a image!");
        }

        Path imagePath = Path.of(fileStorage.getUploadDir(), product.getImage());

        try {
            if (Files.exists(imagePath)) {
                return Files.readAllBytes(imagePath);
            }else return null;
        } catch (IOException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    public void deleteProductImage(String productId) {
        Product product = productService.findProductById(productId);

        if (product.getImage() == null) {
            throw new ImageNotFoundException("This product not contain a image!");
        }

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
