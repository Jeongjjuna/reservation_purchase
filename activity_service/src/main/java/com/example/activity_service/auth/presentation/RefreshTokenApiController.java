package com.example.activity_service.auth.presentation;

import com.example.activity_service.auth.application.RefreshTokenService;
import com.example.activity_service.auth.domain.RefreshTokenInfo;
import com.example.activity_service.auth.presentation.response.RefreshResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/refreshToken")
public class RefreshTokenApiController {

    private final RefreshTokenService refreshTokenService;

    public RefreshTokenApiController(final RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * access 토큰을 재발급한다.
     * body : 리프레쉬 토큰을 받는다.
     */
    @PostMapping
    public ResponseEntity<RefreshResponse> refresh(
            @RequestBody RefreshTokenInfo refreshTokenInfo,
            @RequestHeader("X-Device-UUID") String deviceUUID
    ) {
        return ResponseEntity.ok(refreshTokenService.refresh(refreshTokenInfo, deviceUUID));
    }
}
