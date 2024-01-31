package com.example.newsfeed_service.member.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.newsfeed_service.member.domain.MemberCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("단위테스트 [MemberCreate]")
class MemberCreateTest {

    private MemberCreate givenMemberCreate;

    @BeforeEach
    public void givenFollowerMember() {
        givenMemberCreate = MemberCreate.builder()
                .email("email_1@email.com")
                .password("11111111")
                .name("name")
                .greetings("hello")
                .authenticNumber("authenticationNumber")
                .build();
    }

    @DisplayName("인증번호를 체크시 예외발생 테스트")
    @Test
    void 인증번호를_체크시_예외발생_테스트() {
        assertThatThrownBy(() ->
                givenMemberCreate.checkAuthenticated(null));
        assertThatThrownBy(() ->
                givenMemberCreate.checkAuthenticated("differentNumber"));
        assertThatThrownBy(() ->
                givenMemberCreate.checkAuthenticated(""));
    }

    @DisplayName("비밀번호 8자리 미만이라면 예외발생")
    @ParameterizedTest(name = "비밀번호가 \"{0}\" 일 때")
    @ValueSource(strings = {"", "1", "1234567"})
    void 비밀번호_길이_8자리_미만일때_예외발생(String password) {
        MemberCreate memberCreate = MemberCreate.builder()
                .email("test@email.com")
                .password(password)
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