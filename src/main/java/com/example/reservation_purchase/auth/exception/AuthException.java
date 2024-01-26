package com.example.reservation_purchase.auth.exception;

import com.example.reservation_purchase.exception.GlobalException;

public class AuthException extends GlobalException {

    public AuthException(final AuthErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }

    public static class InvalidPasswordException extends AuthException {
        public InvalidPasswordException(final AuthErrorCode errorCode) {
            super(errorCode);
        }
    }
}