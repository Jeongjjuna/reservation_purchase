package com.example.newsfeed_service.auth.presentation.response;

import lombok.Getter;

@Getter
public class RefreshResponse {

    private final String accessToken;
    private final String refreshToken;

    public RefreshResponse(final String accessToken, final String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static RefreshResponse from(final String accessToken, final String refreshToken) {
        return new RefreshResponse(accessToken, refreshToken);
    }
}
