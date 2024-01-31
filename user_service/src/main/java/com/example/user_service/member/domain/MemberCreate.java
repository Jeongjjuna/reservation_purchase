package com.example.user_service.member.domain;

import com.example.user_service.auth.exception.AuthErrorCode;
import com.example.user_service.auth.exception.AuthException.InvalidAuthenticNumberException;
import com.example.user_service.member.exception.MemberErrorCode;
import com.example.user_service.member.exception.MemberException.InvalidPasswordException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreate {

    private static final int MIN_PASSWORD_LENGTH = 8;

    private String email;
    private String password;
    private String name;
    private String greetings;
    private String authenticNumber;

    @Builder
    public MemberCreate(
            final String email,
            final String password,
            final String name,
            final String greetings,
            final String authenticNumber
    ) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.greetings = greetings;
        this.authenticNumber = authenticNumber;
    }

    public void validate() {
        if (this.password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidPasswordException(MemberErrorCode.INVALID_PASSWORD_ERROR);
        }
    }

    public void checkAuthenticated(final String authenticationNumber) {
        if (authenticationNumber == null) {
            throw new InvalidAuthenticNumberException(AuthErrorCode.INVALID_AUTHENTIC_NUMBER_ERROR);
        }
        if (!this.authenticNumber.equals(authenticationNumber)) {
            throw new InvalidAuthenticNumberException(AuthErrorCode.INVALID_AUTHENTIC_NUMBER_ERROR);
        }
    }
}
