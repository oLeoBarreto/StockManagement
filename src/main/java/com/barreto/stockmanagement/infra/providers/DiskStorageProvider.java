package com.barreto.stockmanagement.infra.providers;

import com.barreto.stockmanagement.infra.config.utils.FileStorage;
import com.barreto.stockmanagement.infra.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class DiskStorageProvider implements StorageProviderUseCase {

    private final FileStorage fileStorage;

    public String uploadFile(MultipartFile file, String fileName) {
        Path uploadPath = Path.of(fileStorage.getUploadPath());
        Path filePath = uploadPath.resolve(fileName);

        try (InputStream inputStream = file.getInputStream()){
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new BadRequestException("Image file is not supported!");
        }

        return fileName;
    }

    public byte[] downloadFile(String fileName) {
        Path imagePath = Path.of(fileStorage.getUploadPath(), fileName);

        try {
            if (Files.exists(imagePath)) {
                return Files.readAllBytes(imagePath);
            }else return null;
        } catch (IOException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    public void deleteFile(String fileName) {
        Path imagePath = Path.of(fileStorage.getUploadPath(), fileName);

        try {
            if (Files.exists(imagePath)) {
                Files.delete(imagePath);
            }
        } catch (IOException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
}
