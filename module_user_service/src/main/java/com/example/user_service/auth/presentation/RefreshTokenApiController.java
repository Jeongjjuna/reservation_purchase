package com.example.user_service.auth.presentation;

import com.example.user_service.auth.application.RefreshTokenService;
import com.example.user_service.auth.domain.RefreshTokenInfo;
import com.example.user_service.auth.presentation.response.RefreshResponse;
import com.example.user_service.common.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class RefreshTokenApiController {

    private final RefreshTokenService refreshTokenService;

    /**
     * access 토큰을 재발급한다.
     * body : 리프레쉬 토큰을 받는다.
     */
    @PostMapping("refreshToken")
    public Response<RefreshResponse> refresh(
            @RequestBody final RefreshTokenInfo refreshTokenInfo,
            @RequestHeader("X-Device-UUID") final String deviceUUID
    ) {
        return Response.success(refreshTokenService.refresh(refreshTokenInfo, deviceUUID));
    }
}
