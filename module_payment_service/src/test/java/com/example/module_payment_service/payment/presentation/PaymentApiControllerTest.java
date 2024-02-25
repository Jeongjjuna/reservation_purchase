package com.example.module_payment_service.payment.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.module_payment_service.payment.application.port.OrderServiceAdapter;
import com.example.module_payment_service.payment.application.port.StockServiceAdapter;
import com.example.module_payment_service.payment.domain.OrderStock;
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
@DisplayName("통합테스트 [Payment]")
class PaymentApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderServiceAdapter orderServiceAdapter;

    @MockBean
    private StockServiceAdapter stockServiceAdapter;

    @DisplayName("상품 생성 테스트 : 성공")
    @Test
    @Disabled
    void 결제_생성_요청() throws Exception {
        // given
        String json = """
                {
                    "orderId" : 1,
                    "memberId" : 2
                }
                """;

        doNothing().when(orderServiceAdapter).complete(any(Long.class));
        doNothing().when(orderServiceAdapter).findById(any(Long.class));
        doNothing().when(orderServiceAdapter).cancel(any(Long.class));
        doNothing().when(stockServiceAdapter).addStock(any(Long.class), any(OrderStock.class));

        // when, then
        mockMvc.perform(post("/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("success"));
    }
}