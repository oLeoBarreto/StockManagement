package com.barreto.stockmanagement.controller.products;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPutRequestBody;
import com.barreto.stockmanagement.useCases.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductControllerTest {
    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

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

        PageImpl<Product> productPage = new PageImpl<>(List.of(product));

        when(productService.createNewProduct(any(ProductPostRequestBody.class))).thenReturn(product);
        when(productService.updateProduct(any(ProductPutRequestBody.class))).thenReturn(product);
        when(productService.findProductById(anyString())).thenReturn(product);
        when(productService.findProductBySupplier(any(Pageable.class), anyString())).thenReturn(productPage);
        when(productService.findProductByCategory(any(Pageable.class), anyString())).thenReturn(productPage);
        when(productService.listAllProducts(any(Pageable.class))).thenReturn(productPage);
        doNothing().when(productService).deleteProduct(anyString());

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should get a page of all existing products")
    void testGetProductsLists() {
        ResponseEntity<Page<Product>> productPage = productController.getProductsLists(PageRequest.of(0, 10));

        assertNotNull(productPage);
        assertEquals("Produto 1", productPage.getBody().toList().get(0).getName());
        assertEquals(HttpStatus.OK, productPage.getStatusCode());
    }

    @Test
    @DisplayName("Should get a page of all existing products by category")
    void testGetProductByCategory() {
        ResponseEntity<Page<Product>> productPage = productController.getProductByCategory(PageRequest.of(0, 10), "test");

        assertNotNull(productPage);
        assertEquals("Produto 1", productPage.getBody().toList().get(0).getName());
        assertEquals(HttpStatus.FOUND, productPage.getStatusCode());
    }

    @Test
    @DisplayName("Should get a page of all existing products by supplier")
    void testGetProductBySupplier() {
        ResponseEntity<Page<Product>> productPage = productController.getProductByCategory(PageRequest.of(0, 10), "test");

        assertNotNull(productPage);
        assertEquals("Produto 1", productPage.getBody().toList().get(0).getName());
        assertEquals(HttpStatus.FOUND, productPage.getStatusCode());
    }

    @Test
    @DisplayName("Should get a existing products by ID")
    void testGetProductById() {
        ResponseEntity<Product> product = productController.getProductById("productId");

        assertNotNull(product);
        assertEquals("Produto 1", product.getBody().getName());
        assertEquals(HttpStatus.FOUND, product.getStatusCode());
    }

    @Test
    @DisplayName("Should post a new product")
    void testPostNewProduct() {
        ProductPostRequestBody productRequestBody = new ProductPostRequestBody(
                "Produto 1",
                "Descricao do produto de teste",
                new BigDecimal(1),
                "test supplier",
                "test"
        );

        ResponseEntity<Product> product = productController.postNewProduct(productRequestBody);

        assertNotNull(product.getBody());
        assertEquals("Produto 1", product.getBody().getName());
        assertEquals(HttpStatus.CREATED, product.getStatusCode());
    }

    @Test
    @DisplayName("Should put a existing product")
    void testPutProduct() {
        ProductPutRequestBody productPutRequestBody = new ProductPutRequestBody(
                "productId",
                "Produto 2",
                "Descricao do produto de teste",
                new BigDecimal(1),
                "test supplier",
                "test"
        );

        ResponseEntity<Product> product = productController.putProduct(productPutRequestBody);

        assertNotNull(product.getBody());
        assertDoesNotThrow(() -> productController.putProduct(productPutRequestBody));
        assertEquals(HttpStatus.OK, product.getStatusCode());
    }

    @Test
    @DisplayName("Should delete a existing product")
    void testDeleteProduct() {
        ResponseEntity<Void> product = productController.deleteProduct("productId");

        assertDoesNotThrow(() -> productController.deleteProduct("productId"));
        assertEquals(HttpStatus.NO_CONTENT, product.getStatusCode());
    }
}