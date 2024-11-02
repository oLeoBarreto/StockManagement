package com.barreto.stockmanagement.useCases.product;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.database.repository.ProductRepository;
import com.barreto.stockmanagement.infra.exceptions.BadRequestException;
import com.barreto.stockmanagement.infra.exceptions.ImageNotFoundException;
import com.barreto.stockmanagement.infra.providers.DiskStorageProvider;
import com.barreto.stockmanagement.infra.providers.S3StorageProvider;
import com.barreto.stockmanagement.infra.providers.StorageProviderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductImageService implements ProductImageUseCase {

    private final ProductService productService;
    private final ProductRepository repository;
    private final DiskStorageProvider diskStorageProvider;
    private final S3StorageProvider s3StorageProvider;

    @Value("${api.storage.driver}")
    private String driver;

    public Product saveProductImage(String productId, MultipartFile image) {
        ValidateImage(image);

        Product product = productService.findProductById(productId);

        String uploadedImage = getStorageProvider().uploadFile(image, generateImageName(product.getId()));

        product.setImage(uploadedImage);
        return repository.save(product);
    }

    public byte[] findProductImage(String productId) {
        Product product = productService.findProductById(productId);

        if (product.getImage() == null) {
            throw new ImageNotFoundException("This product not contain a image!");
        }

        return getStorageProvider().downloadFile(product.getImage());
    }

    public void deleteProductImage(String productId) {
        Product product = productService.findProductById(productId);

        if (product.getImage() == null) {
            throw new ImageNotFoundException("This product not contain a image!");
        }

        getStorageProvider().deleteFile(product.getImage());

        product.setImage(null);
        repository.save(product);
    }

    private static void ValidateImage(MultipartFile image) {
        String imageContentType = image.getContentType();
        List<String> allowedContentType = Arrays.asList(
                MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE
        );

        if (!allowedContentType.contains(imageContentType)) {
            throw new BadRequestException("Image file is not supported! Supported: JPEG, PNG");
        }
    }

    private StorageProviderUseCase getStorageProvider() {
        return Objects.equals("driver", "aws") ? s3StorageProvider : diskStorageProvider;
    }

    private static String generateImageName(String id) {
        return "image-" + id + ".jpeg";
    }
}
