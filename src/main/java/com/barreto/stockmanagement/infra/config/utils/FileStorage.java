package com.barreto.stockmanagement.infra.config.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class FileStorage {
    @Value("${api.storage.directory}")
    private String uploadDiretory;
    @Value("${api.storage.folder}")
    private String uploadFolder;
    private String UploadPath;

    public String getUploadPath() {
        return uploadDiretory + "/" + uploadFolder;
    }
}
