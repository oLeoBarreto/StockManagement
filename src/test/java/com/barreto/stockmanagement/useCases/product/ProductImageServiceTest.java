package com.barreto.stockmanagement.useCases.product;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.config.utils.FileStorage;
import com.barreto.stockmanagement.infra.database.repository.ProductRepository;
import com.barreto.stockmanagement.infra.exceptions.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductImageServiceTest {
    @Mock
    private ProductService productService;
    @Mock
    private ProductRepository repository;
    @Mock
    private FileStorage fileStorage;
    @InjectMocks
    private ProductImageService productImageService;

    @BeforeEach
    void setUp() {
        Product product = new Product(
                "Produto 1",
                "Descricao do produto de teste",
                new BigDecimal(1),
                "test supplier",
                "test",
                null,
                1F
        );
        product.id = "productId";

        when(productService.findProductById(anyString())).thenReturn(product);
        when(repository.save(any(Product.class))).thenReturn(product);
        when(fileStorage.getUploadDir()).thenReturn("src/test/resources/static/images/uploads");

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should be able to upload a image to a existing product")
    void TestSaveNewImageToExistingProduct() {
        MultipartFile imageFile = new MockMultipartFile(
                "product-image.png", "product-image.png", MediaType.IMAGE_PNG_VALUE,"image.png".getBytes());
        var product = productImageService.saveProductImage("productId", imageFile);

        assertNotNull(product.getImage());
        assertEquals(product.getImage(), "image-productId.jpeg");
    }

    @Test
    @DisplayName("Should not be able to upload a image with a invalid file type")
    void TestSaveNewImageWithInvalidType() {
        MultipartFile imageFile = new MockMultipartFile(
                "product-image.gif", "product-image.gif", MediaType.IMAGE_GIF_VALUE,"image.gif".getBytes());

        assertThrows(BadRequestException.class, () -> {
            productImageService.saveProductImage("productId", imageFile);
        });
    }

    @Test
    @DisplayName("Should be able to download a product image")
    void TestGetAProductImage() {
        MultipartFile imageFile = new MockMultipartFile(
                "product-image.png", "product-image.png", MediaType.IMAGE_PNG_VALUE,"image.png".getBytes());
        productImageService.saveProductImage("productId", imageFile);

        var image = productImageService.findProductImage("productId");

        assertNotNull(image);
    }

    @Test
    @DisplayName("Should be able to delete a product image")
    void TestDeleteProductImage() {
        MultipartFile imageFile = new MockMultipartFile(
                "product-image.png", "product-image.png", MediaType.IMAGE_PNG_VALUE,"image.png".getBytes());
        productImageService.saveProductImage("productId", imageFile);

        assertDoesNotThrow(() -> {
            productImageService.deleteProductImage("productId");
        });
    }
}