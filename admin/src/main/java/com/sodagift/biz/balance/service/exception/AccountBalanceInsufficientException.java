package com.sodagift.biz.balance.service.exception;

import com.sodagift.biz.common.BizErrorCode;
import com.sodagift.biz.common.exception.BizException;

public class AccountBalanceInsufficientException extends BizException {

    public AccountBalanceInsufficientException(long userId) {
        super(BizErrorCode.ACCOUNT_BALANCE_INSUFFICIENT, (bizErrorCode -> bizErrorCode.message().formatted(String.valueOf(userId))));
    }
}
