package com.example.reservation_purchase.auth.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenInfo {

    private String refreshToken;

    public RefreshTokenInfo(final String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
