package com.example.reservation_purchase.auth.presentation;

import com.example.reservation_purchase.auth.application.LoginService;
import com.example.reservation_purchase.auth.domain.LoginInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginApiController {

    private final LoginService loginService;

    public LoginApiController(final LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginInfo loginInfo) {
        String token = loginService.login(loginInfo);
        return ResponseEntity.ok(token);
    }
}
