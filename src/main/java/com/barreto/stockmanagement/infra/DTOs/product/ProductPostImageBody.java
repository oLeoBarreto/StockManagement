package com.barreto.stockmanagement.infra.DTOs.product;

import org.springframework.web.multipart.MultipartFile;

public record ProductPostImageBody(String productId, MultipartFile imageFile) {
}
