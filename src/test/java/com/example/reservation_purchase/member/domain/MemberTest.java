package com.example.reservation_purchase.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Member 단위테스트")
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

    @DisplayName("비밀번호 암호화 테스트")
    @Test
    void 비밀번호_암호화테스트() {
        // given
        Member member = Member.builder()
                .email("test@email.com")
                .password("12345678")
                .name("name")
                .greetings("hello")
                .build();

        // when
        member.applyEncodedPassword("encodedPassword");

        // then
        assertThat(member.getPassword()).isEqualTo("encodedPassword");
    }
}