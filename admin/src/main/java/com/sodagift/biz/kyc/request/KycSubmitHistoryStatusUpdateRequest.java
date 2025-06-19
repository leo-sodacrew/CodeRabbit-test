package com.sodagift.biz.kyc.request;

import com.sodagift.biz.domain.account.Account;

public record KycSubmitHistoryStatusUpdateRequest(
        Account.KycStatus status
) {

}