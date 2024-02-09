package com.example.ecommerce_service.product.presentation;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.ecommerce_service.product.application.port.ProductRepository;
import com.example.ecommerce_service.product.application.port.ProductStockRepository;
import com.example.ecommerce_service.product.domain.Product;
import com.example.ecommerce_service.product.domain.ProductStock;
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

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductStockRepository productStockRepository;

    @DisplayName("상품 재고수량 조회 테스트 : 성공")
    @Test
    void 상품_재고_수량_조회_요청() throws Exception {
        // given
        Product product = Product.builder()
                .name("name")
                .content("content")
                .price(20000L)
                .build();
        Product saved = productRepository.save(product);

        ProductStock productStock = ProductStock.builder()
                .productId(saved.getId())
                .stockCount(50)
                .build();
        productStockRepository.save(productStock);

        // when, then
        mockMvc.perform(get("/v1/products/{productId}/stock", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("success"))
                .andExpect(jsonPath("$.data.productId").value(saved.getId()))
                .andExpect(jsonPath("$.data.stockCount").value(50));

    }

    @DisplayName("상품 전체조회 테스트 : 성공")
    @Test
    void 상품_전체_조회_요청() throws Exception {
        // given
        Product product1 = Product.builder()
                .name("name1")
                .content("content1")
                .price(20000L)
                .build();
        productRepository.save(product1);
        Product product2 = Product.builder()
                .name("name2")
                .content("content2")
                .price(20000L)
                .build();
        productRepository.save(product2);

        // when, then
        mockMvc.perform(get("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("success"))
                .andExpect(jsonPath("$.data.content", hasSize(2)));
    }

    @DisplayName("상품 단건조회 테스트 : 성공")
    @Test
    void 상품_단건_조회_요청() throws Exception {
        // given
        Product product = Product.builder()
                .name("name")
                .content("content")
                .price(20000L)
                .build();
        Product saved = productRepository.save(product);

        // when, then
        mockMvc.perform(get("/v1/products/{productId}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("success"))
                .andExpect(jsonPath("$.data.name").value("name"))
                .andExpect(jsonPath("$.data.content").value("content"))
                .andExpect(jsonPath("$.data.price").value("20000"));
    }

    @DisplayName("상품 생성 테스트 : 성공")
    @Test
    void 상품_생성_요청() throws Exception {
        // given
        String json = """
                {
                    "name" : "상품이름",
                    "content" : "상품내용",
                    "price" : "20000",
                    "stockCount" : "50"
                }
                """;

        // when, then
        mockMvc.perform(post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("success"));
    }

}