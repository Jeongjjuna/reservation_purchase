package com.example.reservation_purchase.auth.presentation;

import com.example.reservation_purchase.auth.application.MailService;
import com.example.reservation_purchase.auth.domain.MailForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/mail")
public class MailApiController {

    private final MailService mailService;

    public MailApiController(final MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping
    public ResponseEntity<Void> sendAuthenticationMail(@RequestBody MailForm mailForm) {
        mailService.sendAuthenticationEmail(mailForm.getEmail());
        return ResponseEntity.ok().build();
    }
}
