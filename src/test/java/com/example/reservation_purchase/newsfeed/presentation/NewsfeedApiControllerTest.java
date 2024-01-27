package com.example.reservation_purchase.newsfeed.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Newsfeed 도메인 API 테스트")
class NewsfeedApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    private Member saveMember() {
        Member member = Member.builder()
                .email("user1@naver.com")
                .password("12345678")
                .name("홍길동")
                .greetings("hi")
                .build();
        return memberRepository.save(member);
    }

    private void setPrincipal(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(principal);
    }

    @DisplayName("뉴스피드 생성 테스트 : 성공")
    @Test
    void 뉴스피드_생성_요청() throws Exception {
        // given
        String json = """
                {
                  "receiverId" : %d,
                  "senderId" : %d,
                  "newsfeedType" : "follow",
                  "activityId" : %d
                }
                """.formatted(1L, 2L, 3L);

        // when, then
        mockMvc.perform(post("/api/newsfeeds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @DisplayName("내 뉴스피드 조회 테스트 : 성공")
    @Test
    void 나의_뉴스피드_조회_요청() throws Exception {
        // given
        Member saved = saveMember();
        setPrincipal(saved.getEmail());

        // when, then
        mockMvc.perform(get("/api/newsfeeds/my")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}