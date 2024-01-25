package com.example.reservation_purchase.follow.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.example.reservation_purchase.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class FollowTest {

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        Member followMember = Member.builder()
                .id(1L)
                .email("test1@naver.com")
                .password("11111111")
                .name("name1")
                .greetings("hi1")
                .profileUrl("url1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Member followingMember = Member.builder()
                .id(2L)
                .email("test2@naver.com")
                .password("22222222")
                .name("name2")
                .greetings("hi2")
                .profileUrl("url2")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assertThatCode(() -> Follow.create(followMember, followingMember)
        ).doesNotThrowAnyException();
    }

}