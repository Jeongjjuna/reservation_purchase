package com.example.activity_service.article.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.activity_service.article.application.port.ArticleRepository;
import com.example.activity_service.article.domain.Article;
import com.example.activity_service.client.NewsfeedFeignClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("통합테스트 [ArticleLike]")
class ArticleLikeApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsfeedFeignClient newsfeedFeignClient;

    @Autowired
    private ArticleRepository articleRepository;

    @DisplayName("게시글 좋아요 테스트 : 성공")
    @Test
    void 게시글_좋아요_요청() throws Exception {
        // given
        Article article = Article.builder()
                .writerId(1L)
                .content("content")
                .build();
        Article savedArticle = articleRepository.save(article);

        // when, then
        mockMvc.perform(post("/v1/articles/{articleId}/like", savedArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("member", "1"))
                .andExpect(status().isOk());
    }

}