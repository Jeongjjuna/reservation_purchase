package com.example.activity_service.follow.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [Follow]")
class FollowTest {

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        assertThatCode(() -> Follow.create(1L, 2L)
        ).doesNotThrowAnyException();
    }

}