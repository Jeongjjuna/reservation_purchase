package com.example.user_service.auth.presentation;

import com.example.user_service.auth.application.LoginService;
import com.example.user_service.auth.domain.LoginInfo;
import com.example.user_service.auth.presentation.response.LoginResponse;
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
public class LoginApiController {

    private final LoginService loginService;

    @PostMapping("/login")
    public Response<LoginResponse> login(
            @RequestBody final LoginInfo loginInfo,
            @RequestHeader("X-Device-UUID") final String deviceUUID
    ) {
        final LoginResponse loginResponse = loginService.login(loginInfo, deviceUUID);
        return Response.success(loginResponse);
    }
}
