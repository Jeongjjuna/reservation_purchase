package com.example.activity_service.follow.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.activity_service.client.NewsfeedFeignClient;
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

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("통합테스트 [Follow]")
class FollowApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsfeedFeignClient newsfeedFeignClient;

    @DisplayName("팔로우 테스트 : 성공")
    @Test
    void 팔로우_요청() throws Exception {
        // given
        String json = """
                {
                  "followerMemberId" : 1,
                  "followingMemberId" : 2
                }
                """;

        // when, then
        mockMvc.perform(post("/v1/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .param("member", "1"))
                .andExpect(status().isOk());
    }

    @DisplayName("팔로우 테스트 : 팔로우 하려는 사람이 존재하지 않는 경우")
    @Test
    @Disabled("follow service에서 검증을 수행할지, 아니면 검증된 값 자체를 요청받을지 결정이 필요함")
    void 팔로우_하려는_사람이_존재하지_않는경우_예외발생() throws Exception {
        // given
        String json = """
                {
                  "followerMemberId" : %d,
                  "followingMemberId" : %d
                }
                """.formatted(1L,  9999999L);

        // when, then
        mockMvc.perform(post("/v1/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }
}