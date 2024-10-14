package com.barreto.stockmanagement.controller.products;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.product.ProductPutRequestBody;
import com.barreto.stockmanagement.infra.exceptions.BadRequestExceptionDetail;
import com.barreto.stockmanagement.useCases.product.ProductImageUseCase;
import com.barreto.stockmanagement.useCases.product.ProductUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product operations endpoints")
public class ProductController implements ProductEndpoints {
    private final ProductUseCase productService;
    private final ProductImageUseCase productImageService;

    @GetMapping()
    @Operation(summary = "List all products by your company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Page<Product>> getProductsLists(Pageable pageable, @RequestParam String companyId) {
        return new ResponseEntity<>(productService.listAllProducts(companyId, pageable), HttpStatus.OK);
    }

    @GetMapping("/findByCategory")
    @Operation(summary = "List products by your category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Success response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Page<Product>> getProductByCategory(Pageable pageable, @RequestParam String category, @RequestParam String companyId) {
        return new ResponseEntity<>(productService.findProductByCategory(pageable, category, companyId), HttpStatus.FOUND);
    }

    @GetMapping("/findBySupplier")
    @Operation(summary = "List products by your supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Success response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Page<Product>> getProductBySupplier(Pageable pageable, @RequestParam String supplier, @RequestParam String companyId) {
        return new ResponseEntity<>(productService.findProductBySupplier(pageable, supplier, companyId), HttpStatus.FOUND);
    }

    @GetMapping("/findById")
    @Operation(summary = "Find a existing product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Success found response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Product> getProductById(@RequestParam String id) {
        return new ResponseEntity<>(productService.findProductById(id), HttpStatus.FOUND);
    }

    @GetMapping("/image/download")
    @Operation(summary = "Download the product image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Success found image response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = byte[].class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<byte[]> getProductImage(@RequestParam String productId) {
        return ResponseEntity.status(HttpStatus.FOUND).contentType(MediaType.valueOf("image/png")).body(productImageService.findProductImage(productId));
    }

    @PostMapping()
    @Operation(summary = "Create new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success create response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Product> postNewProduct(@RequestBody @Valid ProductPostRequestBody product) {
        return new ResponseEntity<>(productService.createNewProduct(product), HttpStatus.CREATED);
    }

    @PostMapping("/image/upload")
    @Operation(summary = "Upload image to a existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success upload response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Product> postProductImage(@RequestParam String productId, @RequestParam MultipartFile imageFile) {
        return new ResponseEntity<>(productImageService.saveProductImage(productId, imageFile), HttpStatus.OK);
    }

    @PutMapping()
    @Operation(summary = "Update a existing product data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success update response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Product> putProduct(@RequestBody @Valid ProductPutRequestBody product) {
        return new ResponseEntity<>(productService.updateProduct(product), HttpStatus.OK);
    }

    @DeleteMapping()
    @Operation(summary = "Delete a existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success delete response"),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Void> deleteProduct(@RequestParam String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/image")
    @Operation(summary = "Delete a existing product image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success delete image response"),
            @ApiResponse(responseCode = "400", description = "Bad request response",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestExceptionDetail.class))
                    }
            )
    })
    public ResponseEntity<Void> deleteProductImage(String productId) {
        productImageService.deleteProductImage(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
