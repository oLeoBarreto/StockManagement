package com.barreto.stockmanagement.useCases.product;

import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPutRequestBody;
import com.barreto.stockmanagement.infra.database.repository.ProductRepository;
import com.barreto.stockmanagement.useCases.company.CompanyUseCase;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private CompanyUseCase companyService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        var company = new Company(
                "12.123.123/0001-12",
                "Company test",
                "company@test.com",
                "12345"
        );
        company.setId("companyId");

        Product product = new Product(
                "Produto 1",
                "Descricao do produto de teste",
                new BigDecimal(1),
                "test supplier",
                "test",
                null,
                1F,
                company
        );
        product.id = "productId";

        PageImpl<Product> productPage = new PageImpl<>(List.of(product));

        when(repository.findByCompanyId(any(Pageable.class), anyString())).thenReturn(productPage);
        when(repository.findById(anyString())).thenReturn(Optional.of(product));
        when(repository.findByCategoryAndCompanyId(any(Pageable.class), anyString(), anyString())).thenReturn(productPage);
        when(repository.findBySupplierAndCompanyId(any(Pageable.class), anyString(), anyString())).thenReturn(productPage);
        when(repository.save(any(Product.class))).thenReturn(product);
        doNothing().when(repository).delete(any(Product.class));

        when(companyService.findCompanyById(anyString())).thenReturn(company);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a new product")
    void TestCreateProduct() {
        ProductPostRequestBody productRequestBody = new ProductPostRequestBody(
                "Produto 1",
                "Descricao do produto de teste",
                new BigDecimal(1),
                "test supplier",
                "test",
                "companyId");

        Product product = productService.createNewProduct(productRequestBody);

        assertNotNull(product);
        assertEquals(product.getName(), productRequestBody.name());
    }

    @Test
    @DisplayName("Should find an existing product by your ID")
    void TestFindProductByID() {
        ProductPostRequestBody productRequestBody = new ProductPostRequestBody(
                "Produto 1",
                "Descricao do produto de teste",
                new BigDecimal(1),
                "test supplier",
                "test",
                "companyId");

        Product cratedProduct = productService.createNewProduct(productRequestBody);
        Product foundProduct = productService.findProductById(cratedProduct.id);

        assertEquals(cratedProduct.id, foundProduct.id);
    }

    @Test
    @DisplayName("Should find an existing product by your Supplier")
    void TestFindProductBySupplier() {
        ProductPostRequestBody productRequestBody = new ProductPostRequestBody(
                "Produto 1",
                "Descricao do produto de teste",
                new BigDecimal(1),
                "test supplier",
                "test",
                "companyId");

        productService.createNewProduct(productRequestBody);

        Page<Product> productPage = productService.findProductBySupplier(PageRequest.of(0, 10), "test supplier", "companyId");

        assertFalse(productPage.getContent().isEmpty());
    }

    @Test
    @DisplayName("Should find an existing product by your Category")
    void TestFindProductByCategory() {
        ProductPostRequestBody productRequestBody = new ProductPostRequestBody(
                "Produto 1",
                "Descricao do produto de teste",
                new BigDecimal(1),
                "test supplier",
                "test",
                "companyId");

        Product createdProduct = productService.createNewProduct(productRequestBody);

        Page<Product> productPage = productService.findProductByCategory(PageRequest.of(0, 10), createdProduct.getCategory(), createdProduct.getCompany().getId());

        assertFalse(productPage.getContent().isEmpty());
    }

    @Test
    @DisplayName("Should list all products")
    void TestListAllProducts() {
        ProductPostRequestBody productRequestBody = new ProductPostRequestBody(
                "Produto 1",
                "Descricao do produto de teste",
                new BigDecimal(1),
                "test supplier",
                "test",
                "companyId"
        );

        productService.createNewProduct(productRequestBody);

        Page<Product> productPage = productService.listAllProducts("companyId", PageRequest.of(0, 10));

        assertFalse(productPage.getContent().isEmpty());
    }

    @Test
    @DisplayName("Should be able to update a existing product")
    void TestUpdateAnExistProduct() {
        ProductPostRequestBody productRequestBody = new ProductPostRequestBody(
                "Produto 1",
                "Descricao do produto de teste",
                new BigDecimal(1),
                "test supplier",
                "test",
                "companyId"
        );

        Product product = productService.createNewProduct(productRequestBody);

        ProductPutRequestBody productPutRequestBody = new ProductPutRequestBody(
                product.id,
                "Produto 2",
                "Descricao do produto de teste",
                new BigDecimal(1),
                "test supplier",
                "test"
        );

        Product updatedProduct = productService.updateProduct(productPutRequestBody);

        assertNotNull(updatedProduct);
        assertEquals(product.id, updatedProduct.id);
    }

    @Test
    @DisplayName("Should be able to delete a existing product")
    void TestDeleteAnExistProduct() {
        ProductPostRequestBody productRequestBody = new ProductPostRequestBody(
                "Produto 1",
                "Descricao do produto de teste",
                new BigDecimal(1),
                "test supplier",
                "test",
                "companyId"
        );

        Product product = productService.createNewProduct(productRequestBody);

        productService.deleteProduct(product.id);

        assertTrue(repository.findAll().isEmpty());
    }
}