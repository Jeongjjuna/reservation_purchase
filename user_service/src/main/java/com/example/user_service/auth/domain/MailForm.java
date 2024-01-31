package com.example.user_service.auth.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MailForm {

    private String email;

    public MailForm(final String email) {
        this.email = email;
    }
}
