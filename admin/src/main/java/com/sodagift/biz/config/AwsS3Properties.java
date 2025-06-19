package com.sodagift.biz.config;


import javax.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

@Validated
public class AwsS3Properties {

    @NotEmpty
    private String accessKey;
    @NotEmpty
    private String secretKey;
    @NotEmpty
    private String region;
    @NotEmpty
    private String basePath;
    @NotEmpty
    private String bucketName;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
