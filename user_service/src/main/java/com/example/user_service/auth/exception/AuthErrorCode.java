package com.example.user_service.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode {
    INVALID_PASSWORD_ERROR(HttpStatus.BAD_REQUEST, "[ERROR] 비밀번호가 올바르지 않습니다."),
    INVALID_AUTHENTIC_NUMBER_ERROR(HttpStatus.BAD_REQUEST, "[ERROR] 인증번호가 올바르지 않습니다."),
    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, "[ERROR] 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;

    AuthErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
