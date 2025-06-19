package com.sodagift.biz.common.exception;

import com.sodagift.biz.common.BizErrorCode;
import java.util.function.Function;

public abstract class BizException extends RuntimeException {

    private final BizErrorCode errorCode;

    /**
     * message에 변수 처리가 필요한 경우 사용합니다.
     */
    protected BizException(BizErrorCode errorCode, Function<BizErrorCode, String> messageCreator) {
        super(messageCreator.apply(errorCode));
        this.errorCode = errorCode;
    }

    /**
     * message를 커스텀하게 설정하기 위해 사용합니다.
     */
    protected BizException(BizErrorCode errorCode, String mesaage) {
        super(mesaage);
        this.errorCode = errorCode;
    }


    public BizErrorCode errorCode() {
        return errorCode;
    }
}
