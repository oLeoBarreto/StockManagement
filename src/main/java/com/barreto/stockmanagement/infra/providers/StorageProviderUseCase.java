package com.barreto.stockmanagement.infra.providers;

import org.springframework.web.multipart.MultipartFile;

public interface StorageProviderUseCase {
    String uploadFile(MultipartFile file, String fileName);
    byte[] downloadFile(String fileName);
    void deleteFile(String fileName);
}
