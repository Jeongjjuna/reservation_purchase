package com.example.newsfeed_service.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.example.newsfeed_service.member.domain.Member;
import com.example.newsfeed_service.member.domain.MemberCreate;
import com.example.newsfeed_service.member.domain.MemberUpdate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [Member]")
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

    @DisplayName("프로필이미지 저장 테스트")
    @Test
    void 프로필이미지_저장테스트() {
        // given
        Member member = Member.builder()
                .email("test@email.com")
                .password("12345678")
                .name("name")
                .greetings("hello")
                .build();

        // when
        member.saveProfile("url");

        // then
        assertThat(member.getProfileUrl()).isEqualTo("url");
    }

    @DisplayName("이미 프로필을 등록한 상태인지 확인 테스트")
    @Test
    void 프로필_이미지가_업로드된_상태인지_테스트() {
        // given
        Member member = Member.builder()
                .email("test@email.com")
                .password("12345678")
                .name("name")
                .greetings("hello")
                .build();

        // when
        boolean expectedFalse = member.isProfileUploaded();
        member.saveProfile("url");
        boolean expectedTrue = member.isProfileUploaded();

        // then
        assertAll(
                () -> assertThat(expectedFalse).isFalse(),
                () -> assertThat(expectedTrue).isTrue()
        );

    }

    @DisplayName("이미 프로필을 등록한 상태인지 확인 테스트")
    @Test
    void 회원정보_업데이트_테스트() {
        // given
        Member member = Member.builder()
                .email("test@email.com")
                .password("12345678")
                .name("name")
                .greetings("hello")
                .build();
        MemberUpdate memberUpdate = MemberUpdate.builder()
                .name("newName")
                .greetings("newGreetings")
                .build();

        // when
        member.update(memberUpdate);

        // then
        assertAll(
                () -> assertThat(member.getName()).isEqualTo("newName"),
                () -> assertThat(member.getGreetings()).isEqualTo("newGreetings")
        );

    }
}