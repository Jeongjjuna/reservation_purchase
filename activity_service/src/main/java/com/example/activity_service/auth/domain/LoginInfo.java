package com.example.activity_service.auth.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginInfo {

    private String email;
    private String password;

    @Builder
    public LoginInfo(final String email, final String password) {
        this.email = email;
        this.password = password;
    }
}
