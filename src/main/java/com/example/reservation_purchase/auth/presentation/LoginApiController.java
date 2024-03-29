package com.example.reservation_purchase.auth.presentation;

import com.example.reservation_purchase.auth.application.LoginService;
import com.example.reservation_purchase.auth.domain.LoginInfo;
import com.example.reservation_purchase.auth.presentation.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/login")
public class LoginApiController {

    private final LoginService loginService;

    public LoginApiController(final LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginInfo loginInfo,
            @RequestHeader("X-Device-UUID") String deviceUUID
    ) {
        LoginResponse loginResponse = loginService.login(loginInfo, deviceUUID);
        return ResponseEntity.ok(loginResponse);
    }
}
