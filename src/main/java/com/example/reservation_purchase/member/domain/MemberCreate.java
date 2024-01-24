package com.example.reservation_purchase.member.domain;

import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException.InvalidPasswordException;
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

    @Builder
    public MemberCreate(final String email, final String password, final String name, final String greetings) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.greetings = greetings;
    }

    public void validate() {
        if (this.password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidPasswordException(MemberErrorCode.INVALID_PASSWORD_ERROR);
        }
    }
}
