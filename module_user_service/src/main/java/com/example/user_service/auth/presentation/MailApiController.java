package com.example.user_service.auth.presentation;

import com.example.user_service.auth.application.MailService;
import com.example.user_service.auth.domain.MailForm;
import com.example.user_service.common.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class MailApiController {

    private final MailService mailService;

    @PostMapping("mail")
    public Response<Void> sendAuthenticationMail(@RequestBody final MailForm mailForm) {
        mailService.sendAuthenticationEmail(mailForm.getEmail());
        return Response.success();
    }
}
