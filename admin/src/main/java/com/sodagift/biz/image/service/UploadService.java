package com.sodagift.biz.image.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    String upload(MultipartFile file);

    @Service
    @Profile("local")
    class MockUploadService implements UploadService {

        @Override
        public String upload(MultipartFile file) {
            return "mock";
        }
    }
}
