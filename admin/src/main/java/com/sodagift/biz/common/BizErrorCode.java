package com.sodagift.biz.common;

import static com.sodagift.common.test.mockMvc.snippets.CodeDescriptionFieldsSnippet.CodeLegend;
import org.springframework.http.HttpStatus;

public enum BizErrorCode implements CodeLegend {
    NOT_FOUND("not_found", null, HttpStatus.NOT_FOUND),
    INVALID_REQUEST("invalid_request", null, HttpStatus.BAD_REQUEST),

    ACCOUNT_ALREADY_REGISTERED("account_already_registered", "Account('%s') is already registered", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVATED("account_not_activated", "Account('%s') is not activated", HttpStatus.BAD_REQUEST),
    ITEM_NOT_FOUND("item_not_found", "Biz Item('%s') is not found", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND("order_not_found", "Biz Order('%s') is not found", HttpStatus.NOT_FOUND),
    ACCOUNT_NOT_FOUND("account_not_found", "Account('%s') is not found", HttpStatus.NOT_FOUND),
    ORDER_ALREADY_CREATED("order_already_created", "An Order with externalReferenceId('%s') has already been created", HttpStatus.BAD_REQUEST),
    ORDER_ALREADY_CANCELED("order_already_canceled", "An Order with externalReferenceId('%s') has already been canceled", HttpStatus.BAD_REQUEST),
    ORDER_NOT_COMPOSED_SINGLE_BULK("order_not_composed_single_bulk", "An Order with externalReferenceId('%s') is not composed of single bulk",
            HttpStatus.BAD_REQUEST),
    BULK_ALREADY_CREATED("bulk_already_created", "A Bulk with bulkId('%s') has already been created", HttpStatus.BAD_REQUEST),
    ACCOUNT_BALANCE_INSUFFICIENT("account_balance_insufficient", "Account('%s') balance is insufficient", HttpStatus.BAD_REQUEST),
    BULK_NOT_FOUND("bulk_not_found", "Bulk('%s') is not found", HttpStatus.NOT_FOUND),
    LEDGER_NOT_FOUND("ledger_not_found", "Ledger('%s') is not found", HttpStatus.NOT_FOUND),
    ORDER_ALREADY_ISSUED("order_already_issued", "An Order with bizOrderId('%s') has already been issued", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    BizErrorCode(String errorCode, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public String code() {
        return errorCode;
    }

    public String message() {
        return errorMessage;
    }

    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String getCodeName() {
        return code();
    }

    @Override
    public String getDescription() {
        return message();
    }
}
