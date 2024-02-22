package com.example.module_product_service.product.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.module_product_service.product.application.port.ProductRepository;
import com.example.module_product_service.product.application.port.ReservationTimeRepository;
import com.example.module_product_service.product.application.port.StockServiceAdapter;
import com.example.module_product_service.product.domain.Product;
import com.example.module_product_service.product.domain.ProductStock;
import com.example.module_product_service.product.domain.ReservationTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

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
    private ReservationTimeRepository reservationTimeRepository;

    @MockBean
    private StockServiceAdapter stockServiceAdapter;

    private Long saveProduct() {
        Product product = Product.builder()
                .name("name")
                .content("content")
                .price(20000L)
                .build();
        Product saved = productRepository.save(product);

        ReservationTime reservationTime = ReservationTime.builder()
                .productId(saved.getId())
                .reservationStartAt(LocalDateTime.now())
                .build();
        reservationTimeRepository.save(reservationTime);

        return saved.getId();
    }

    @DisplayName("상품 정보 수정 테스트 : 성공")
    @Test
    void 상품_정보_수정_요청() throws Exception {
        // given
        Long productId = saveProduct();
        String json = """
                {
                    "name" : "이름수정",
                    "content" : "내용수정",
                    "price" : 10000,
                    "reservationStartAt" : "2024-02-07T12:30:00",
                    "stockCount" : 100
                }
                """;

        doNothing().when(stockServiceAdapter).updateStockCount(any(Long.class), any(ProductStock.class));

        // when, then
        mockMvc.perform(put("/v1/products/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("success"));
    }

    @DisplayName("상품 전체조회 테스트 : 성공")
    @Test
    void 상품_전체_조회_요청() throws Exception {
        // given
        Long productId = saveProduct();
        Product product = Product.builder()
                .name("name")
                .content("content")
                .price(10000L)
                .build();
        productRepository.save(product);

        // when, then
        mockMvc.perform(get("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("success"));
    }

    @DisplayName("상품 단건조회 테스트 : 성공")
    @Test
    void 상품_단건_조회_요청() throws Exception {
        // given
        Long productId = saveProduct();

        // when, then
        mockMvc.perform(get("/v1/products/{productId}", productId)
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
                    "reservationStartAt" : "2024-02-07T12:30:00",
                    "stockCount" : "50"
                }
                """;

        doNothing().when(stockServiceAdapter).createStockCount(any(Long.class), any(ProductStock.class));

        // when, then
        mockMvc.perform(post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("success"));
    }

}