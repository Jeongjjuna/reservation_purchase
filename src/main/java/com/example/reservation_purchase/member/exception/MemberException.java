package com.example.reservation_purchase.member.exception;

import com.example.reservation_purchase.exception.GlobalException;

public class MemberException extends GlobalException {

    public MemberException(final MemberErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }

    public static class InvalidPasswordException extends MemberException {
        public InvalidPasswordException(final MemberErrorCode errorCode) {
            super(errorCode);
        }
    }

    public static class MemberDuplicatedException extends MemberException {
        public MemberDuplicatedException(final MemberErrorCode errorCode) {
            super(errorCode);
        }
    }
}
