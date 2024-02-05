package com.example.user_service.member.domain;

import com.example.user_service.member.exception.MemberErrorCode;
import com.example.user_service.member.exception.MemberException.InvalidPasswordException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordUpdate {

    private static final int MIN_PASSWORD_LENGTH = 8;

    private String password;

    public PasswordUpdate(final String password) {
        this.password = password;
    }

    public void validate() {
        if (this.password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidPasswordException(MemberErrorCode.INVALID_PASSWORD_ERROR);
        }
    }
}
