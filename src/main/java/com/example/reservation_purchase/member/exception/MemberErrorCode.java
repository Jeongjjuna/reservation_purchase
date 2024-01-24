package com.example.reservation_purchase.member.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode {
    INVALID_PASSWORD_ERROR(HttpStatus.BAD_REQUEST, "[ERROR] 비밀번호는 8자리 이상이어야 합니다.");

    private final HttpStatus status;
    private final String message;

    MemberErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
