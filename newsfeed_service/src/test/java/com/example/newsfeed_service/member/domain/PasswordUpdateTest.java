package com.example.newsfeed_service.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.newsfeed_service.member.domain.PasswordUpdate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("단위테스트 [PasswordUpdate]")
class PasswordUpdateTest {
    @DisplayName("비밀번호 8자리 미만이라면 예외발생")
    @ParameterizedTest(name = "비밀번호가 \"{0}\" 일 때")
    @ValueSource(strings = {"", "1", "1234567"})
    void 비밀번호_길이_8자리_미만일때_예외발생(String password) {
        // given
        PasswordUpdate passwordUpdate = new PasswordUpdate(password);

        assertThatThrownBy(() -> passwordUpdate.validate());
    }
}