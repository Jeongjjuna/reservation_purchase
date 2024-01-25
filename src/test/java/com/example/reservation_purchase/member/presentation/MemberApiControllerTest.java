package com.example.reservation_purchase.member.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Member 도메인 API 테스트")
class MemberApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원가입 테스트 : 성공")
    @Test
    void 회원가입_요청() throws Exception {
        // given
        String json = """
                {
                  "email" : "user1@naver.com",
                  "password" : "12345678",
                  "name" : "정지훈",
                  "greetings" : "hello"
                }
                """;

        // when, then
        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @DisplayName("회원가입 테스트 : 비밀번호 8자리 미만일 경우 예외발생")
    @Test
    void 회원가입_요청시_비밀번호가_8자리_미만이면_예외발생() throws Exception {
        // given
        String json = """
                {
                  "email" : "user1@naver.com",
                  "password" : "1234567",
                  "name" : "정지훈",
                  "greetings" : "hello"
                }
                """;

        // when, then
        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원가입 테스트 : 이미 가입된 email이 있을 경우 예외발생")
    @Test
    void 회원가입_요청시_email이_중복되면__예외발생() throws Exception {
        // given
        Member member = Member.builder()
                .email("user1@naver.com")
                .password("12345678")
                .name("홍길동")
                .greetings("hi")
                .build();
        memberRepository.save(member);
        String json = """
                {
                  "email" : "user1@naver.com",
                  "password" : "12345678",
                  "name" : "정지훈",
                  "greetings" : "hello"
                }
                """;

        // when, then
        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());
    }
}