package com.example.reservation_purchase.auth.presentation.response;

import lombok.Getter;

@Getter
public class LoginResponse {

    private final String accessToken;
    private final String refreshToken;

    public LoginResponse(final String accessToken, final String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static LoginResponse from(final String accessToken, final String refreshToken) {
        return new LoginResponse(accessToken, refreshToken);
    }
}
