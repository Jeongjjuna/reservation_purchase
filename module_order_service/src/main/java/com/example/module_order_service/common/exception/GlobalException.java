package com.example.module_order_service.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String detailMessage;

    public GlobalException(final HttpStatus httpStatus, final String detailMessage) {
        this.httpStatus = httpStatus;
        this.detailMessage = detailMessage;
    }
}
