package com.example.reservation_purchase.newsfeed.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.domain.MemberCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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