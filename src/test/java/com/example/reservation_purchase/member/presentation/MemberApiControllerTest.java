package com.example.reservation_purchase.member.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    // TODO : 예외발생시 응답코드를 반환하도록 구현, 그리고 테스트도 작성해야함
}