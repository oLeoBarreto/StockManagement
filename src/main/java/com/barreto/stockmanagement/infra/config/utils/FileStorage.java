package com.barreto.stockmanagement.infra.config.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class FileStorage {
    @Value("${api.files.uploadDir}")
    private String UploadDir;
}
