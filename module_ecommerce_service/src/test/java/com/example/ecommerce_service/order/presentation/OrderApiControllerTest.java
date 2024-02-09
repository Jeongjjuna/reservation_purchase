package com.example.ecommerce_service.order.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
@DisplayName("통합테스트 [Order]")
class OrderApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("주문 생성 테스트 : 성공")
    @Test
    void 주문_생성_요청() throws Exception {
        // given
        String json = """
                {
                    "productId" : 5,
                    "productType" : "reservationProduct",
                    "quantity" : 2,
                    "memberId" : 99,
                    "address" : "서울특별시 xxx동 xxx아파트 xx호"
                }
                """;

        // when, then
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("success"));
    }
}