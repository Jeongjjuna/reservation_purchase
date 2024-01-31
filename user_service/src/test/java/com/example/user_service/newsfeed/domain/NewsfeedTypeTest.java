package com.example.user_service.newsfeed.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [NewsfeedType]")
class NewsfeedTypeTest {

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        assertThatCode(() -> NewsfeedType.create("follow")
        ).doesNotThrowAnyException();
    }

    @DisplayName("없는유형의 타입을 입력받으면 예외발생")
    @Test
    void 생성시_없는유형의_타입을_입력받으면_예외발생() {
        assertThatThrownBy(() -> NewsfeedType.create("Unknown"));
    }
}