package com.sodagift.biz.kyc.request;

public record KycCriteria(
        Long accountId,
        String status
) {

}