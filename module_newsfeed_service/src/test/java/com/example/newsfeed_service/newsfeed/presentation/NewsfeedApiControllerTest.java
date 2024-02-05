package com.example.newsfeed_service.newsfeed.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.newsfeed_service.newsfeed.client.ActivityClient;
import com.example.newsfeed_service.newsfeed.client.FollowingIdList;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("통합테스트 [Newsfeed]")
class NewsfeedApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityClient activityClient;

    @DisplayName("뉴스피드 생성 테스트 : 성공")
    @Test
    @Disabled("mongodb 트랜잭션 처리가능 하도록 변경해야 한다.")
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
    @Disabled("mongodb 트랜잭션 처리가능 하도록 변경해야 한다.")
    void 나의_뉴스피드_조회_요청() throws Exception {

        when(activityClient.findFollowing(any())).thenReturn(new FollowingIdList("", "", Arrays.asList(1L, 2L, 3L)));

        // when, then
        mockMvc.perform(get("/v1/newsfeeds")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}