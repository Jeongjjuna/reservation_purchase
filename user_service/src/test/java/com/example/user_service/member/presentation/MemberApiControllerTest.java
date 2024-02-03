package com.example.user_service.member.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.user_service.auth.application.port.RedisMailRepository;
import com.example.user_service.member.application.port.MemberRepository;
import com.example.user_service.member.client.ActivityClient;
import com.example.user_service.member.client.MemberList;
import com.example.user_service.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("통합테스트 [Member]")
class MemberApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @MockBean
    private RedisMailRepository redisMailRepository;

    @MockBean
    private ActivityClient activityClient;

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

    @DisplayName("회원가입 테스트 : 성공")
    @Test
    void 회원가입_요청() throws Exception {
        // given
        String json = """
                {
                  "email" : "user1@naver.com",
                  "password" : "12345678",
                  "name" : "정지훈",
                  "greetings" : "hello",
                  "authenticNumber" : "123456"
                }
                """;

        when(redisMailRepository.getData(any())).thenReturn("123456");

        // when, then
        mockMvc.perform(post("/v1/members")
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
                  "greetings" : "hello",
                  "authenticNumber" : "123456"
                }
                """;

        // when, then
        mockMvc.perform(post("/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("failure"))
                .andExpect(jsonPath("$.data.status").value("BAD_REQUEST"));
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
                  "greetings" : "hello",
                  "authenticNumber" : "123456"
                }
                """;

        // when, then
        mockMvc.perform(post("/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("failure"))
                .andExpect(jsonPath("$.data.status").value("CONFLICT"));
    }

    @DisplayName("회원정보 수정 테스트 : 이름, 인사말을 수정할 수 있다.")
    @Test
    void 회원수정_테스트() throws Exception {
        // given
        Member saved = saveMember();
        setPrincipal(saved.getEmail());
        String json = """
                {
                  "name" : "정지훈",
                  "greetings" : "hello"
                }
                """;

        // when, then
        mockMvc.perform(patch("/v1/members/{memberId}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @DisplayName("회원정보 수정 테스트 : 권한이 없으면 예외발생")
    @Test
    void 권한이_없는경우_회원수정_테스트() throws Exception {
        // given
        Member saved = saveMember();
        setPrincipal(saved.getEmail());
        String json = """
                {
                  "name" : "정지훈",
                  "greetings" : "hello"
                }
                """;

        // when, then
        mockMvc.perform(patch("/v1/members/{memberId}", 9999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("failure"))
                .andExpect(jsonPath("$.data.status").value("UNAUTHORIZED"));
    }

    @DisplayName("비밀번호 수정 테스트 : 비밀번호를 수정할 수 있다.")
    @Test
    void 비밀번호_수정_테스트() throws Exception {
        // given
        Member saved = saveMember();
        setPrincipal(saved.getEmail());
        String json = """
                {
                "password" : " abcedfgh"
                }
                """;

        // when, then
        mockMvc.perform(patch("/v1/members/{memberId}/password", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @DisplayName("비밀번호 수정 테스트 : 권한이 없으면 예외발생")
    @Test
    void 권한이_없는경우_비밀번호수정_테스트() throws Exception {
        // given
        Member saved = saveMember();
        setPrincipal(saved.getEmail());
        String json = """
                {
                "password" : " abcedfgh"
                }
                """;

        // when, then
        mockMvc.perform(patch("/v1/members/{memberId}/password", 9999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("failure"))
                .andExpect(jsonPath("$.data.status").value("UNAUTHORIZED"));
    }

    @DisplayName("회원 단건 조회 테스트 : 권한이 없으면 예외발생")
    @Test
    void 권한이_없는경우_회원단건조회_테스트() throws Exception {
        // given
        Member saved = saveMember();
        setPrincipal(saved.getEmail());

        // when, then
        mockMvc.perform(get("/v1/members/{memberId}", 9999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("failure"))
                .andExpect(jsonPath("$.data.status").value("UNAUTHORIZED"));
    }

    @DisplayName("회원 단건 조회 테스트 : 회원 정보를 조회 할 수 있다.")
    @Test
    void 회원단건조회_테스트() throws Exception {
        // given
        Member saved = saveMember();
        setPrincipal(saved.getEmail());

        // when, then
        mockMvc.perform(get("/v1/members/{memberId}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("내가 팔로우한 회원들 조회 테스트 : 내가 팔로우한 사람들의 목록을 조회할 수 있다.")
    @Test
    void 내가_팔로우한_회원들_조회() throws Exception {
        when(activityClient.findFollowing(any())).thenReturn(new MemberList("", "", Arrays.asList(1L, 2L, 3L)));

        // when, then
        mockMvc.perform(get("/v1/members/my-followings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("member", "1"))
                .andExpect(status().isOk());
    }

    @DisplayName("나를 팔로우한 회원들 조회 테스트 : 나를 팔로우한 사람들의 목록을 조회할 수 있다.")
    @Test
    void 나를_팔로우한_회원들_조회() throws Exception {
        when(activityClient.findFollowers(any())).thenReturn(new MemberList("", "", Arrays.asList(1L, 2L, 3L)));

        // when, then
        mockMvc.perform(get("/v1/members/my-followers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("member", "1"))
                .andExpect(status().isOk());
    }
}