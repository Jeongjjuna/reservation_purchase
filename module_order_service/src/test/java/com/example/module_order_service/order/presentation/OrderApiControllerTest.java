package com.example.module_order_service.order.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.module_order_service.order.application.port.OrderRepository;
import com.example.module_order_service.order.application.port.ReservationProductStockAdapter;
import com.example.module_order_service.order.domain.Order;
import com.example.module_order_service.order.domain.ProductType;
import com.example.module_order_service.order.domain.ReservationProductStock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("통합테스트 [Order]")
class OrderApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @MockBean
    private ReservationProductStockAdapter reservationProductStockAdapter;


    private Long saveOrder() {
        Order order = Order.builder()
                .productId(1L)
                .productType(ProductType.RESERVATION_PRODUCT)
                .quantity(2L)
                .memberId(3L)
                .address("서울특별시 xxx동 xxx아파트 xx호")
                .build();
        Order saved = orderRepository.save(order);
        return saved.getId();
    }

    @DisplayName("주문 단건조회 테스트 : 성공")
    @Test
    void 주문_단건_조회_요청() throws Exception {
        // given
        Long orderId = saveOrder();
        System.out.println(orderId);

        // when, then
        mockMvc.perform(get("/v1/orders/{orderId}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("success"))
                .andExpect(jsonPath("$.data.productId").value("1"))
                .andExpect(jsonPath("$.data.productType").value("reservationProduct"))
                .andExpect(jsonPath("$.data.quantity").value("2"))
                .andExpect(jsonPath("$.data.memberId").value("3"))
                .andExpect(jsonPath("$.data.address").value("서울특별시 xxx동 xxx아파트 xx호"));
    }

    @DisplayName("주문 생성 테스트 : 성공")
    @Test
    void 주문_생성_요청() throws Exception {
        // given
        String json = """
                {
                    "productId" : 1,
                    "productType" : "reservationProduct",
                    "quantity" : 2,
                    "memberId" : 99,
                    "address" : "서울특별시 xxx동 xxx아파트 xx호"
                }
                """;

        when(reservationProductStockAdapter.findById(any(Long.class)))
                .thenReturn(Optional.of(new ReservationProductStock(1L, 3)));
        when(reservationProductStockAdapter.update(any(ReservationProductStock.class)))
                .thenReturn(new ReservationProductStock(1L, 3));

        // when, then
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desc").value("success"));
    }
}