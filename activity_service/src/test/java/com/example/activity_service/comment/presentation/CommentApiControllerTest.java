package com.example.activity_service.comment.presentation;

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
@DisplayName("통합테스트 [Comment]")
class CommentApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsfeedFeignClient newsfeedFeignClient;

    @Autowired
    private ArticleRepository articleRepository;

    @DisplayName("댓글 생성 테스트 : 성공")
    @Test
    void 댓글_생성_요청() throws Exception {
        // given
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
                        .content(json)
                        .param("member", "1"))
                .andExpect(status().isOk());
    }
}