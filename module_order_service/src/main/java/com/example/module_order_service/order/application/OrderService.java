package com.example.module_order_service.order.application;


import com.example.module_order_service.common.exception.GlobalException;
import com.example.module_order_service.order.application.port.OrderRepository;
import com.example.module_order_service.order.application.port.ReservationProductStockRepository;
import com.example.module_order_service.order.domain.Order;
import com.example.module_order_service.order.domain.OrderCreate;
import com.example.module_order_service.order.domain.ReservationProductStock;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ReservationProductStockRepository reservationProductStockRepository;

    /**
     * 결제 화면 들어가기 버튼 클릭 시 주문 생성이 요청된다.
     * 주문 생성
     */
    @Transactional
    public Long create(final OrderCreate orderCreate) {

        // TODO : 거의 동시에 100~1000개 이상 들어올 수 도 있음(동시 클릭)
        /**
         * 원자적으로 처리해야 함(시작)
         */
        // TODO : feign client 요청 구현 예정
        reservationProductStockRepository.findById(orderCreate.getProductId())
                .map(ReservationProductStock::validateStock) // 만약 재고가 0 이하라면 예외 발생
                .map(ReservationProductStock::subtractStockByOne) // 재고개수 - 1 해주기
                .map(reservationProductStockRepository::update) // 변경된 재고 수량 저장
                .orElseThrow(() -> new IllegalArgumentException("해당 상품 재고를 찾을 수 없음"));
        /**
         * 원자적으로 처리해야 함(끝)
         */

        final Long savedOrderId = Optional.of(Order.create(orderCreate))
                .map(orderRepository::save)
                .map(Order::getId)
                .orElseThrow(() -> new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "[ERROR] product save fail"));

        return savedOrderId;
    }

    /**
     * 고객 변심으로 결제 화면 창을 떠날 때 요청된다.
     * 주문 취소
     */
    @Transactional
    public void cancel(final Long orderId) {
        final Order order  = orderRepository.findById(orderId)
                .map(Order::cancel) // 주문 취소 하기
                .map(orderRepository::save) // 주문 취소 저장하기
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] not found order"));

        /**
         * 원자적으로 처리해야 함(시작)
         */
        // TODO : feign client 요청 구현 예정
        reservationProductStockRepository.findById(order.getProductId())
                .map(ReservationProductStock::addStockByOne) // 재고개수 + 1 해주기
                .map(reservationProductStockRepository::update) // 변경된 재고 수량 저장
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] not found product stock"));
        /**
         * 원자적으로 처리해야 함(끝)
         */
    }
}
