package com.example.newsfeed_service.common.exception;

import com.example.newsfeed_service.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(GlobalException.class)
    public Response<ErrorResponse> handleGlobalException(final GlobalException e) {
        log.error("Error occurs {}", e.toString());
        return Response.error(ErrorResponse.builder()
                .status(e.getHttpStatus())
                .message(e.getDetailMessage())
                .timestamp(LocalDateTime.now())
                .build()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public Response<ErrorResponse> handleGlobalException(final RuntimeException e) {
        log.error("Error occurs {}", e.toString());
        return Response.error(ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("[ERROR] check server error log")
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
}
