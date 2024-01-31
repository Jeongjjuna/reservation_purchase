package com.example.user_service.exception;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final LocalDateTime timestamp;
    private final String message;

    public ErrorResponse(final LocalDateTime timestamp, final String message) {
        this.timestamp = timestamp;
        this.message = message;
    }
}
