package com.example.reservation_purchase.member.domain;

import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException;
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
            throw new MemberException.InvalidPasswordException(MemberErrorCode.INVALID_PASSWORD_ERROR);
        }
    }
}
