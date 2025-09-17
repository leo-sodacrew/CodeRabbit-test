package com.sodagift.biz.kyc;

import com.sodagift.biz.kyc.request.KycCriteria;
import com.sodagift.biz.kyc.request.KycSubmitHistoryStatusUpdateRequest;
import com.sodagift.biz.kyc.response.KycSubmitHistoryResponse;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/biz/kyc")
public class KycResource {

    private final KycService kycService;

    public KycResource(KycService kycService) {
        this.kycService = kycService;
    }

    @GetMapping
    public Page<KycSubmitHistoryResponse> findAll(KycCriteria criteria, @PageableDefault(size = 25) Pageable pageable) {
        return kycService.findAll(criteria, pageable);
    }

    @GetMapping(params = "ids")
    public List<KycSubmitHistoryResponse> getListByIds(@RequestParam Set<String> ids) {
        return kycService.findByIds(ids);
    }

    @GetMapping("/{id}")
    public KycSubmitHistoryResponse getOne(@PathVariable String id) {
        return kycService.findById(UUID.fromString(id));
    }

    @GetMapping("/{id}/files")
    public ResponseEntity<Resource> downloadFiles(@PathVariable String id) {
        try {
            Long accountIdById = kycService.getAccountIdById(UUID.fromString(id));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/zip"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"kyc_files_%d.zip\"".formatted(accountIdById))
                    .body(kycService.loadFileAsResource(UUID.fromString(id)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/confirm/{id}")
    public KycSubmitHistoryResponse updateStatus(@PathVariable String id, @RequestBody KycSubmitHistoryStatusUpdateRequest request) {
        return kycService.updateStatus(UUID.fromString(id), request);
    }
}