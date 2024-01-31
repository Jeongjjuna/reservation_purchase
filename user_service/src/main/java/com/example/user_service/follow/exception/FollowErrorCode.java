package com.example.user_service.follow.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FollowErrorCode {
    UNAUTHORIZED_ACCESS_ERROR(HttpStatus.UNAUTHORIZED, "[ERROR] 권한이 없습니다."),
    FOLLOW_DUPLICATED(HttpStatus.CONFLICT, "[ERROR] 이미 팔로우를 했습니다.");

    private final HttpStatus status;
    private final String message;

    FollowErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
