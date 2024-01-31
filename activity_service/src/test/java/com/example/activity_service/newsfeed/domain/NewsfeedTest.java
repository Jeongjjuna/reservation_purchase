package com.example.activity_service.newsfeed.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.example.activity_service.newsfeed.domain.Newsfeed;
import com.example.activity_service.newsfeed.domain.NewsfeedCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [Newsfeed]")
class NewsfeedTest {

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        NewsfeedCreate newsfeedCreate = NewsfeedCreate.builder()
                .receiverId(1L)
                .senderId(2L)
                .newsfeedType("follow")
                .build();

        assertThatCode(() -> Newsfeed.create(newsfeedCreate)
        ).doesNotThrowAnyException();
    }
}