package com.example.reservation_purchase.member.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        MemberCreate memberCreate = MemberCreate.builder()
                .email("test@email.com")
                .password("1234567")
                .name("name")
                .greetings("hello")
                .build();

        assertThatCode(() -> Member.create(memberCreate)
        ).doesNotThrowAnyException();
    }
}