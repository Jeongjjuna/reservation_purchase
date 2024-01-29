package com.example.reservation_purchase.auth.presentation;

import com.example.reservation_purchase.auth.application.LogoutService;
import com.example.reservation_purchase.auth.domain.LogoutInfo;
import com.example.reservation_purchase.auth.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logout")
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
