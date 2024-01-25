package com.example.reservation_purchase.member.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MemberCreate 단위테스트")
class MemberCreateTest {

    @DisplayName("비밀번호 8자리 미만이라면 예외발생")
    @Test
    void 비밀번호_길이_8자리_미만일때_예외발생() {
        MemberCreate memberCreate = MemberCreate.builder()
                .email("test@email.com")
                .password("1234567")
                .name("name")
                .greetings("hello")
                .build();

        assertThatThrownBy(() -> memberCreate.validate());
    }

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        assertThatCode(() -> MemberCreate.builder()
                .email("test@email.com")
                .password("1234567")
                .name("name")
                .greetings("hello")
                .build()
        ).doesNotThrowAnyException();
    }
}