package com.sodagift.biz.thirdparty.aws.s3;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.sodagift.biz.config.thirdparty.AwsS3Properties;
import java.io.InputStream;

public class AwsS3Service {

    private final AmazonS3 awsS3;
    private final String bucketName;

    public AwsS3Service(AwsS3Properties properties) {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(
                properties.getAccessKey(), properties.getSecretKey());
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(basicAWSCredentials);
        this.awsS3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .withRegion(properties.getRegion())
                .build();
        this.bucketName = properties.getBucketName();
    }

    public void upload(InputStream input, String contentType, long contentLength, String path) {
        if (!awsS3.doesBucketExistV2(bucketName)) {
            awsS3.createBucket(bucketName);
        }
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(contentLength);
        awsS3.putObject(new PutObjectRequest(bucketName, path, input, metadata));
    }

    public S3Object getObject(String key) {
        return awsS3.getObject(bucketName, key);
    }
}