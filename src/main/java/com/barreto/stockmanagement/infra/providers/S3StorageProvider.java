package com.barreto.stockmanagement.infra.providers;

import com.barreto.stockmanagement.infra.config.aws.AwsClientConfig;
import com.barreto.stockmanagement.infra.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class S3StorageProvider implements StorageProviderUseCase {

    private final S3Client s3Client;
    private final AwsClientConfig awsClientConfig;

    public String uploadFile(MultipartFile file, String fileName) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(awsClientConfig.getBucketName())
                    .key(fileName)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );

            return fileName;
        } catch (S3Exception | IOException e) {
            throw new BadRequestException("Error to upload file to S3 bucket: " + e.getMessage());
        }
    }

    public byte[] downloadFile(String fileName) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(awsClientConfig.getBucketName())
                    .key(fileName)
                    .build();

            ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(getObjectRequest);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;

            while ((length = s3Object.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }

            return byteArrayOutputStream.toByteArray();
        } catch (S3Exception | IOException e) {
            throw new BadRequestException("Error to download file from S3 bucket: " + e.getMessage());
        }
    }

    public void deleteFile(String fileName) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(awsClientConfig.getBucketName())
                    .key(fileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            throw new BadRequestException("Error to delete file from S3 bucket: " + e.getMessage());
        }
    }
}
