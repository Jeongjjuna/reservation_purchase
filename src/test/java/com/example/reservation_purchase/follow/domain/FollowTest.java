package com.example.reservation_purchase.follow.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.example.reservation_purchase.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

@DisplayName("단위테스트 [Follow]")
class FollowTest {

    private Member givenFollowerMember;

    private Member givenFollowingMember;

    @BeforeEach
    public void givenFollowerMember() {
        givenFollowerMember = Member.builder()
                .id(1L)
                .email("email_1@naver.com")
                .password("11111111")
                .name("name_1")
                .greetings("hello_1")
                .profileUrl("url_1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        givenFollowingMember = Member.builder()
                .id(1L)
                .email("email_2@naver.com")
                .password("22222222")
                .name("name2")
                .greetings("hello_2")
                .profileUrl("url_2")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        assertThatCode(() -> Follow.create(givenFollowerMember, givenFollowingMember)
        ).doesNotThrowAnyException();
    }

}