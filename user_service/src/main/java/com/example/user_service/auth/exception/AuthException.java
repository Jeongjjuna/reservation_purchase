package com.example.user_service.auth.exception;

import com.example.user_service.exception.GlobalException;

public class AuthException extends GlobalException {

    public AuthException(final AuthErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }

    public static class InvalidPasswordException extends AuthException {
        public InvalidPasswordException(final AuthErrorCode errorCode) {
            super(errorCode);
        }
    }

    public static class InvalidAuthenticNumberException extends AuthException {
        public InvalidAuthenticNumberException(final AuthErrorCode errorCode) {
            super(errorCode);
        }
    }

    public static class UnauthorizedException extends AuthException {
        public UnauthorizedException(final AuthErrorCode errorCode) {
            super(errorCode);
        }
    }
}
