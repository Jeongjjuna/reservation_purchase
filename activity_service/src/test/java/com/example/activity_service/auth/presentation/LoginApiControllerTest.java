package com.example.activity_service.auth.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.activity_service.auth.application.port.RedisRefreshRepository;
import com.example.activity_service.member.application.port.MemberRepository;
import com.example.activity_service.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("통합테스트 [Auth]")
class LoginApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @MockBean
    private RedisRefreshRepository redisRefreshRepository;

    private Member setMember() {
        Member member = Member.builder()
                .email("user1@naver.com")
                .password(passwordEncoder.encode("12345678"))
                .name("홍길동")
                .greetings("hi")
                .build();
        return memberRepository.save(member);
    }

    @DisplayName("로그인 테스트 : 이 기기에서 이미 로그인 한 경우 성공")
    @Test
    void 로그인_요청() throws Exception {
        // given
        Member saved = setMember();
        String json = """
                {
                  "email" : "user1@naver.com",
                  "password" : "12345678"
                }
                """;

        when(redisRefreshRepository.findByValue(any())).thenReturn("1111");

        // when, then
        mockMvc.perform(post("/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Device-UUID", "1111")
                        .content(json))
                .andExpect(status().isOk());
    }

    @DisplayName("로그인 테스트 : 가입되지 않은 이메일로 로그인시 예외발생")
    @Test
    void 가입되지_않은_이메일로_로그인_요청시_예외발생() throws Exception {
        // given
        String json = """
                {
                  "email" : "user1@naver.com",
                  "password" : "12345678"
                }
                """;

        // when, then
        mockMvc.perform(post("/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Device-UUID", "1111")
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @DisplayName("로그경인 테스트 : 비밀번호가 일치하지 않으면 예외발생")
    @Test
    void 비밀번호가_일치하지_않으면_예외발생() throws Exception {
        // given
        Member saved = setMember();
        String json = """
                {
                  "email" : "user1@naver.com",
                  "password" : "87654321"
                }
                """;

        // when, then
        mockMvc.perform(post("/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}