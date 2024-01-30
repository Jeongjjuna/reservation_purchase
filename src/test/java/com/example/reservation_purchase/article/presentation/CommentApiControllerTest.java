package com.example.reservation_purchase.article.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.reservation_purchase.article.application.port.ArticleRepository;
import com.example.reservation_purchase.article.domain.Article;
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
@DisplayName("통합테스트 [Comment]")
class CommentApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

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

    @DisplayName("댓글 생성 테스트 : 성공")
    @Test
    void 댓글_생성_요청() throws Exception {
        // given
        Member saved = saveMember();
        setPrincipal(saved.getEmail());

        Article article = Article.builder()
                .writerId(1L)
                .content("content")
                .build();
        Article savedArticle = articleRepository.save(article);

        String json = """
                {
                  "articleId" : %d,
                  "content" : "comment"
                }
                """.formatted(savedArticle.getId());

        // when, then
        mockMvc.perform(post("/v1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}