package com.example.newsfeed_service.exception.presentation;

import com.example.newsfeed_service.exception.ErrorResponse;
import com.example.newsfeed_service.exception.GlobalException;
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
    public ResponseEntity<ErrorResponse> handleGlobalException(final GlobalException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new ErrorResponse(LocalDateTime.now(), e.getDetailMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(final RuntimeException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(LocalDateTime.now(), e.toString()));
    }
}
