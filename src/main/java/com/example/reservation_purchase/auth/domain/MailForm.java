package com.example.reservation_purchase.auth.domain;

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
