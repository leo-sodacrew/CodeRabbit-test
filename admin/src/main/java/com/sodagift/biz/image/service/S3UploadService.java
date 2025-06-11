package com.sodagift.biz.image.service;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sodagift.biz.config.AwsS3Properties;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Profile("!local")
public class S3UploadService implements UploadService {

    private static final String MEDIA_URL = "https://media.sodagift.com";

    private final AmazonS3 awsS3;
    private final String basePath;
    private final String bucketName;

    public S3UploadService(AwsS3Properties properties) {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(
                properties.getAccessKey(), properties.getSecretKey());
        AWSCredentialsProvider credentialsProvider =
                new AWSStaticCredentialsProvider(basicAWSCredentials);

        this.awsS3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .withRegion(properties.getRegion())
                .build();
        this.basePath = properties.getBasePath();
        this.bucketName = properties.getBucketName();
    }

    @Override
    public String upload(MultipartFile file) {
        var metadata = createMetadata(file.getContentType(), file.getSize());
        var path = createPath(file.getContentType());
        try {
            upload(file.getInputStream(), metadata, path);
            return "%s/%s".formatted(MEDIA_URL, path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String createPath(String contentType) {
        String imageType = contentType.split("/")[1];
        return "%s%s/%s.%s".formatted(basePath, "biz/brand", UUID.randomUUID(), imageType);
    }

    private void upload(InputStream input, ObjectMetadata metadata, String path) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, input, metadata);
        putObjectRequest.setAccessControlList(grantAccess());

        awsS3.putObject(putObjectRequest);
    }

    private AccessControlList grantAccess() {
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        return acl;
    }

    private ObjectMetadata createMetadata(String contentType, long contentLength) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(contentLength);
        return metadata;
    }
}
