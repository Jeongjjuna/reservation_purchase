package com.example.ecommerce_service.product.presentation;

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
@DisplayName("통합테스트 [Product]")
class ProductApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("상품 생성 테스트 : 성공")
    @Test
    void 상품_생성_요청() throws Exception {
        // given
        String json = """
                {
                    "name" : "상품이름",
                    "content" : "상품내용",
                    "price" : "20000"
                }
                """;

        // when, then
        mockMvc.perform(post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

}