package com.example.module_product_service.common.exception;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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
