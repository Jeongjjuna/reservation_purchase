package com.example.activity_service.article.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
@DisplayName("통합테스트 [Article]")
class ArticleApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("게시글 생성 테스트 : 성공")
    @Test
    void 게시글_생성_요청() throws Exception {
        // given
        String json = """
                {
                  "content" : "게시글 내용"
                }
                """;

        // when, then
        mockMvc.perform(post("/v1/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("member", "1")
                        .content(json))
                .andExpect(status().isCreated());
    }

    @DisplayName("내가 팔로우한 사람들의 게시글 조회 : 성공")
    @Test
    void 나의_팔로우한_사람들의_게시글_조회_요청() throws Exception {
        // when, then
        mockMvc.perform(get("/v1/articles/my-follows")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}