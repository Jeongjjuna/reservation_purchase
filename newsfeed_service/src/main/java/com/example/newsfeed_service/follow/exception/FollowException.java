package com.example.newsfeed_service.follow.exception;

import com.example.newsfeed_service.exception.GlobalException;

public class FollowException extends GlobalException {

    public FollowException(final FollowErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }

    public static class FollowUnauthorizedException extends FollowException {
        public FollowUnauthorizedException(final FollowErrorCode errorCode) {
            super(errorCode);
        }
    }

    public static class FollowDuplicatedException extends FollowException {
        public FollowDuplicatedException(final FollowErrorCode errorCode) {
            super(errorCode);
        }
    }
}
