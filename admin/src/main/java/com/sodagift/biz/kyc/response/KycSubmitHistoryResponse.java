package com.sodagift.biz.kyc.response;

import com.sodagift.biz.common.HostHolder;
import com.sodagift.biz.domain.account.Account;
import com.sodagift.biz.domain.account.kyc.ExpectedSpendAmountTextConverter;
import com.sodagift.biz.domain.account.kyc.KycSubmitHistory;
import com.sodagift.biz.domain.account.kyc.SpendType;
import com.sodagift.biz.domain.auth.vo.GroupType;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record KycSubmitHistoryResponse(
        UUID id,
        Long accountId,
        String companyName,
        GroupType groupType,
        String companyTaxId,
        String purpose,
        String expectedSpendAmount,
        SpendType spendType,
        Account.KycStatus status,
        String downloadLink,
        Instant createdAt,
        Instant updatedAt
) {

    public KycSubmitHistoryResponse(UUID id, Long accountId, String companyName, GroupType groupType, String companyTaxId, String purpose,
            int expectedSpendAmount,
            SpendType spendType, Account.KycStatus status, Set<String> fileNames, Instant createdAt, Instant updatedAt
    ) {
        this(id, accountId, companyName, groupType, companyTaxId, purpose, new ExpectedSpendAmountTextConverter(expectedSpendAmount).value(),
                spendType, status,
                downloadLink(id, fileNames), createdAt, updatedAt);
    }

    public static KycSubmitHistoryResponse from(KycSubmitHistory kycSubmitHistory) {
        return new KycSubmitHistoryResponse(
                kycSubmitHistory.id(),
                kycSubmitHistory.account().id(),
                kycSubmitHistory.account().companyName(),
                kycSubmitHistory.account().type(),
                kycSubmitHistory.account().taxId(),
                kycSubmitHistory.purpose(),
                kycSubmitHistory.expectedSpendAmount(),
                kycSubmitHistory.spendType(),
                kycSubmitHistory.account().kycStatus(),
                downloadLink(kycSubmitHistory.id(), kycSubmitHistory.fileNames()),
                kycSubmitHistory.createdAt(),
                kycSubmitHistory.updatedAt()
        );
    }

    private static String downloadLink(UUID id, Set<String> fileNames) {
        if (fileNames.isEmpty()) {
            return null;
        }
        return "%s/biz/kyc/%s/files".formatted(HostHolder.HOST, id.toString());
    }

    public int statusOrder() {
        return status.sortOrder();
    }
}