package com.example.newsfeed_service.auth.presentation;

import com.example.newsfeed_service.auth.application.LogoutService;
import com.example.newsfeed_service.auth.domain.LogoutInfo;
import com.example.newsfeed_service.auth.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/logout")
public class LogoutApiController {

    private final LogoutService logoutService;

    public LogoutApiController(final LogoutService logoutService) {
        this.logoutService = logoutService;
    }

    @PostMapping
    public ResponseEntity<Void> logout(
            @RequestBody LogoutInfo logoutInfo,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestHeader("X-Device-UUID") String deviceUUID
    ) {
        logoutService.logout(logoutInfo, userDetails.getEmail(), deviceUUID);
        return ResponseEntity.ok().build();
    }

    /**
     * 모든 기기에서 로그아웃 하도록 구현
     */
    @PostMapping("/all-device")
    public ResponseEntity<Void> logoutAll(
            @RequestBody LogoutInfo logoutInfo,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestHeader("X-Device-UUID") String deviceUUID
    ) {
        logoutService.logoutAll(logoutInfo, userDetails.getEmail());
        return ResponseEntity.ok().build();
    }
}
