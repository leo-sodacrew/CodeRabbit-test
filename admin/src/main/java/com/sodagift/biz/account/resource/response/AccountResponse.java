package com.sodagift.biz.account.resource.response;

import com.sodagift.biz.domain.account.Account;

public record AccountResponse(
        Long id,
        String name,
        String email
) {

    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.id(),
                account.name(),
                account.email().value()
        );
    }
} 