package com.example.newsfeed_service.article.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.newsfeed_service.article.application.port.ArticleRepository;
import com.example.newsfeed_service.article.domain.Article;
import com.example.newsfeed_service.member.application.port.MemberRepository;
import com.example.newsfeed_service.member.domain.Member;
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
@DisplayName("통합테스트 [ArticleLike]")
class ArticleLikeApiControllerTest {

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

    @DisplayName("게시글 좋아요 테스트 : 성공")
    @Test
    void 게시글_좋아요_요청() throws Exception {
        // given
        Member saved = saveMember();
        setPrincipal(saved.getEmail());

        Article article = Article.builder()
                .writerId(1L)
                .content("content")
                .build();
        Article savedArticle = articleRepository.save(article);

        // when, then
        mockMvc.perform(post("/v1/articles/{articleId}/like", savedArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}