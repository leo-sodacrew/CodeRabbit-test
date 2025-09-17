package com.sodagift.biz.kyc;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.sodagift.biz.domain.account.kyc.KycCompletedEvent;
import com.sodagift.biz.domain.account.kyc.KycSubmitHistory;
import com.sodagift.biz.domain.account.kyc.KycSubmitHistoryRepository;
import com.sodagift.biz.kyc.request.KycCriteria;
import com.sodagift.biz.kyc.request.KycSubmitHistoryStatusUpdateRequest;
import com.sodagift.biz.kyc.response.KycSubmitHistoryResponse;
import com.sodagift.biz.thirdparty.aws.s3.AwsS3Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KycService {

    private final KycSubmitHistoryRepository kycSubmitHistoryRepository;
    private final KycQueryService kycQueryService;
    private final AwsS3Service awsS3Service;
    private final ApplicationEventPublisher eventPublisher;

    public KycService(KycSubmitHistoryRepository kycSubmitHistoryRepository, KycQueryService kycQueryService, AwsS3Service awsS3Service,
            ApplicationEventPublisher eventPublisher) {
        this.kycSubmitHistoryRepository = kycSubmitHistoryRepository;
        this.kycQueryService = kycQueryService;
        this.awsS3Service = awsS3Service;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    public Page<KycSubmitHistoryResponse> findAll(KycCriteria criteria, Pageable pageable) {
        return new PageImpl<>(kycQueryService.findAll(criteria, pageable).stream()
                .sorted(Comparator.comparing(KycSubmitHistoryResponse::statusOrder))
                .toList(),
                pageable, kycQueryService.count(criteria));
    }

    @Transactional(readOnly = true)
    public KycSubmitHistoryResponse findById(UUID id) {
        return kycSubmitHistoryRepository.findById(id)
                .map(KycSubmitHistoryResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("KYC not found"));
    }

    @Transactional(readOnly = true)
    public List<KycSubmitHistoryResponse> findByIds(Set<String> ids) {
        return kycSubmitHistoryRepository.findAllById(ids.stream()
                        .map(UUID::fromString)
                        .toList())
                .stream()
                .map(KycSubmitHistoryResponse::from)
                .sorted(Comparator.comparing(KycSubmitHistoryResponse::statusOrder))
                .toList();
    }

    @Transactional
    public KycSubmitHistoryResponse updateStatus(UUID id, KycSubmitHistoryStatusUpdateRequest request) {
        KycSubmitHistory kycSubmitHistory = kycSubmitHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("KYC not found"));

        kycSubmitHistory.account().activate();
        eventPublisher.publishEvent(new KycCompletedEvent(kycSubmitHistory.accountId()));
        return KycSubmitHistoryResponse.from(kycSubmitHistory);
    }

    @Transactional(readOnly = true)
    public Resource loadFileAsResource(UUID id) {
        KycSubmitHistory kyc = kycSubmitHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("KYC not found"));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ZipOutputStream zos = new ZipOutputStream(baos)) {

            for (String file : kyc.fileNames()) {
                S3ObjectInputStream s3Object = awsS3Service.getObject("kyc/" + kyc.accountId() + "/" + file).getObjectContent();

                ZipEntry zipEntry = new ZipEntry(file);
                zos.putNextEntry(zipEntry);

                byte[] buffer = new byte[1024];
                int len;
                while ((len = s3Object.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }

                zos.closeEntry();
                s3Object.close();
            }

            zos.finish();
            return new ByteArrayResource(baos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to create zip file", e);
        }
    }

    @Transactional(readOnly = true)
    public Long getAccountIdById(UUID uuid) {
        return kycSubmitHistoryRepository.findById(uuid)
                .map(KycSubmitHistory::accountId)
                .orElseThrow(() -> new IllegalArgumentException("KYC not found"));
    }
}