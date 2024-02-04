package com.example.user_service.auth.presentation;

import com.example.user_service.auth.application.LoginService;
import com.example.user_service.auth.domain.LoginInfo;
import com.example.user_service.auth.presentation.response.LoginResponse;
import com.example.user_service.common.response.Response;
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
    public Response<LoginResponse> login(
            @RequestBody LoginInfo loginInfo,
            @RequestHeader("X-Device-UUID") String deviceUUID
    ) {
        LoginResponse loginResponse = loginService.login(loginInfo, deviceUUID);
        return Response.success(loginResponse);
    }
}
