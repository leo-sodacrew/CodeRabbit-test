package com.sodagift.biz.config.web.exception;

import com.sodagift.biz.common.BizErrorCode;

public record ErrorResponse(String errorCode, String message) {

    public static ErrorResponse of(BizErrorCode errorCode, String message) {
        return new ErrorResponse(errorCode.code(), message);
    }
}
