package com.example.module_product_service.common.exception;

import com.example.module_product_service.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity handleGlobalException(final GlobalException e) {
        log.error("Error occurs {}", e.toString());
        Response body = Response.error(ErrorResponse.builder()
                .status(e.getHttpStatus())
                .message(e.getDetailMessage())
                .timestamp(LocalDateTime.now())
                .build());
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(body);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleGlobalException(final RuntimeException e) {
        log.error("Error occurs {}", e.getMessage());
        Response body = Response.error(ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }
}
