package com.example.reservation_purchase.newsfeed.presentation;

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
@DisplayName("Newsfeed 도메인 API 테스트")
class NewsfeedApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("뉴스피드 생성 테스트 : 성공")
    @Test
    void 뉴스피드_생성_요청() throws Exception {
        // given
        String json = """
                {
                  "receiverId" : %d,
                  "senderId" : %d,
                  "newsfeedType" : "follow"
                }
                """.formatted(1L, 2L);

        // when, then
        mockMvc.perform(post("/api/newsfeed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}