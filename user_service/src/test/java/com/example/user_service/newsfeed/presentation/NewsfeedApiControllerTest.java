package com.example.user_service.newsfeed.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.user_service.member.application.port.MemberRepository;
import com.example.user_service.member.domain.Member;
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
@DisplayName("통합테스트 [Newsfeed]")
class NewsfeedApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    private Member anyRegisteredMember;

    /**
     * @param email email 로 회원가입하고, 인증정보를 세팅해준다.
     */
    private void givenAuthenticatedMember(String email) {
        // 임의로 회원을 한명 저장한다.
        Member member = Member.builder()
                .email(email)
                .password("11111111")
                .name("name")
                .greetings("hello")
                .build();
        anyRegisteredMember = memberRepository.save(member);

        // 저장된 회원으로 인증했다고 가정한다.
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
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
        mockMvc.perform(post("/v1/newsfeeds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @DisplayName("내 뉴스피드 조회 테스트 : 성공")
    @Test
    void 나의_뉴스피드_조회_요청() throws Exception {
        // given
        givenAuthenticatedMember("email@naver.com");

        // when, then
        mockMvc.perform(get("/v1/newsfeeds/my")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}