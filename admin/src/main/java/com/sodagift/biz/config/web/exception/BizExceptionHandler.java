package com.sodagift.biz.config.web.exception;

import com.sodagift.common.exception.NotFoundException;
import com.sodagift.biz.common.BizErrorCode;
import com.sodagift.biz.common.exception.BizException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BizExceptionHandler {

    @ExceptionHandler(BizException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(BizException exception) {
        var bizErrorCode = exception.errorCode();
        return ResponseEntity.status(bizErrorCode.httpStatus())
                .body(ErrorResponse.of(bizErrorCode, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(BizErrorCode.INVALID_REQUEST.httpStatus())
                .body(ErrorResponse.of(BizErrorCode.INVALID_REQUEST, bindingErrorMessage(exception.getBindingResult())));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.status(BizErrorCode.NOT_FOUND.httpStatus())
                .body(ErrorResponse.of(BizErrorCode.NOT_FOUND, exception.getErrorMessage()));
    }

//    @ExceptionHandler(ItemNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleItemNotFoundException(ItemNotFoundException exception) {
//        return ResponseEntity.status(BizErrorCode.ITEM_NOT_FOUND.httpStatus())
//                .body(ErrorResponse.of(BizErrorCode.ITEM_NOT_FOUND, exception.getMessage()));
//    }
//
//    @ExceptionHandler(BizOrderNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleBizOrderNotFoundException(BizOrderNotFoundException exception) {
//        return ResponseEntity.status(BizErrorCode.ORDER_NOT_FOUND.httpStatus())
//                .body(ErrorResponse.of(BizErrorCode.ORDER_NOT_FOUND, exception.getMessage()));
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(BizErrorCode.INVALID_REQUEST.httpStatus())
                .body(ErrorResponse.of(BizErrorCode.INVALID_REQUEST, exception.getMessage()));
    }

//    @ExceptionHandler(OrderAlreadyCreatedException.class)
//    public ResponseEntity<ErrorResponse> handleOrderAlreadyCreatedException(OrderAlreadyCreatedException exception) {
//        return ResponseEntity.status(BizErrorCode.ORDER_ALREADY_CREATED.httpStatus())
//                .body(ErrorResponse.of(BizErrorCode.ORDER_ALREADY_CREATED, exception.getMessage()));
//    }

    private String bindingErrorMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .findFirst()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return "%s %s".formatted(fieldError.getField(), fieldError.getDefaultMessage());
                    }
                    return error.getDefaultMessage();
                }).orElse("Invalid Request");
    }
}
