package com.example.reservation_purchase.exception.presentation;

import com.example.reservation_purchase.exception.ErrorResponse;
import com.example.reservation_purchase.exception.GlobalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGlobalException(final GlobalException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new ErrorResponse(LocalDateTime.now(), e.getDetailMessage()));
    }
}
