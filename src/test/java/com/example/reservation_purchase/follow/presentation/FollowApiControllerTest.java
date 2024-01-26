package com.example.reservation_purchase.follow.presentation;

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
@DisplayName("Follow 도메인 API 테스트")
class FollowApiControllerTest {

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

    @DisplayName("팔로우 테스트 : 성공")
    @Test
    void 팔로우_요청() throws Exception {
        // given
        Member saved = saveMember();
        setPrincipal(saved.getEmail());

        Member member = Member.builder()
                .email("user2@naver.com")
                .password("12345678")
                .name("홍길동")
                .greetings("hi")
                .build();
        Member followingMember = memberRepository.save(member);

        String json = """
                {
                  "followerMemberId" : %d,
                  "followingMemberId" : %d
                }
                """.formatted(saved.getId(), followingMember.getId());

        // when, then
        mockMvc.perform(post("/api/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @DisplayName("팔로우 테스트 : 팔로우 하려는 사람이 존재하지 않는 경우")
    @Test
    void 팔로우_하려는_사람이_존재하지_않는경우_예외발생() throws Exception {
        // given
        Member saved = saveMember();
        setPrincipal(saved.getEmail());

        Member member = Member.builder()
                .email("user2@naver.com")
                .password("12345678")
                .name("홍길동")
                .greetings("hi")
                .build();
        Member followingMember = memberRepository.save(member);

        String json = """
                {
                  "followerMemberId" : %d,
                  "followingMemberId" : %d
                }
                """.formatted(saved.getId(), 9999999L);

        // when, then
        mockMvc.perform(post("/api/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }
}