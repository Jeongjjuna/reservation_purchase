package com.example.reservation_purchase.auth.presentation;

import com.example.reservation_purchase.auth.application.LogoutService;
import com.example.reservation_purchase.auth.domain.LogoutInfo;
import com.example.reservation_purchase.auth.domain.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        logoutService.logout(logoutInfo, userDetails.getEmail());
        return ResponseEntity.ok().build();
    }
}