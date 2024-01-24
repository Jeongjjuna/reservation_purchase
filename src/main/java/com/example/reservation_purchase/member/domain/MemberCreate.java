package com.example.reservation_purchase.member.domain;

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
            throw new IllegalArgumentException("비밀번호는 8자리 이상이어야 함");
        }
    }
}
