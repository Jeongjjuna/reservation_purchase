package com.example.module_stock_service.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final HttpStatus status;
    private final String message;
    private final LocalDateTime timestamp;

    @Builder
    public ErrorResponse(final HttpStatus status, final String message, final LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
