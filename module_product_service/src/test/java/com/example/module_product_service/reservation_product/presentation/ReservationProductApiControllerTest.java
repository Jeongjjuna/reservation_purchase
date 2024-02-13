package com.example.module_product_service.reservation_product.presentation;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.ecommerce_service.reservation_product.application.port.ReservationProductRepository;
import com.example.ecommerce_service.reservation_product.application.port.ReservationProductStockRepository;
import com.example.ecommerce_service.reservation_product.domain.ReservationProduct;
import com.example.ecommerce_service.reservation_product.domain.ReservationProductStock;
import java.time.LocalDateTime;
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
@DisplayName("통합테스트 [ReservationProduct]")
class ReservationProductApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationProductRepository reservationProductRepository;

    @Autowired
    private ReservationProductStockRepository reservationProductStockRepository;

    private Long saveProduct() {
        ReservationProduct reservationProduct = ReservationProduct.builder()
                .name("name")
                .content("content")
                .price(20000L)
                .purchaseButtonActivationAt(LocalDateTime.now())
                .build();
        ReservationProduct saved = reservationProductRepository.save(reservationProduct);

        ReservationProductStock reservationProductStock = ReservationProductStock.builder()
                .productId(saved.getId())
                .stockCount(50)
                .build();
        reservationProductStockRepository.save(reservationProductStock);

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
                    "purchaseButtonActivationAt" : "2024-02-07T12:30:00",
                    "stockCount" : 100
                }
                """;

        // when, then
        mockMvc.perform(put("/v1/reservation-products/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("success"));
    }

    @DisplayName("상품 재고수량 조회 테스트 : 성공")
    @Test
    void 상품_재고_수량_조회_요청() throws Exception {
        // given
        Long productId = saveProduct();

        // when, then
        mockMvc.perform(get("/v1/reservation-products/{productId}/stock", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("success"))
                .andExpect(jsonPath("$.data.productId").value(productId))
                .andExpect(jsonPath("$.data.stockCount").value(50));
    }

    @DisplayName("상품 전체조회 테스트 : 성공")
    @Test
    void 상품_전체_조회_요청() throws Exception {
        // given
        Long productId = saveProduct();
        ReservationProduct reservationProduct = ReservationProduct.builder()
                .name("name")
                .content("content")
                .price(10000L)
                .purchaseButtonActivationAt(LocalDateTime.now())
                .build();
        reservationProductRepository.save(reservationProduct);

        // when, then
        mockMvc.perform(get("/v1/reservation-products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("success"))
                .andExpect(jsonPath("$.data.content", hasSize(2)));
    }

    @DisplayName("상품 단건조회 테스트 : 성공")
    @Test
    void 상품_단건_조회_요청() throws Exception {
        // given
        Long productId = saveProduct();

        // when, then
        mockMvc.perform(get("/v1/reservation-products/{productId}", productId)
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
                    "purchaseButtonActivationAt" : "2024-02-07T12:30:00",
                    "stockCount" : "50"
                }
                """;

        // when, then
        mockMvc.perform(post("/v1/reservation-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("success"));
    }

}