package com.example.ecommerce_service.order.application;

import com.example.ecommerce_service.order.application.port.OrderRepository;
import com.example.ecommerce_service.order.domain.Order;
import com.example.ecommerce_service.order.domain.OrderCreate;
import com.example.ecommerce_service.reservation_product.application.port.ReservationProductStockRepository;
import com.example.ecommerce_service.reservation_product.domain.ReservationProductStock;
import lombok.AllArgsConstructor;
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

        reservationProductStockRepository.findById(orderCreate.getProductId())
                .map(ReservationProductStock::validateStock) // 만약 재고가 0 이하라면 예외 발생
                .map(ReservationProductStock::subtractStockByOne) // 재고개수 -1 해주기
                .map(reservationProductStockRepository::save) // 변경된 재고 수량 저장
                .orElseThrow(() -> new IllegalArgumentException("해당 상품 재고를 찾을 수 없음"));

        final Order order = Order.create(orderCreate);
        final Order saved = orderRepository.save(order);

        return saved.getId();
    }

    /**
     * 고객 변심으로 결제 화면 창을 떠날 때 요청된다.
     * 주문 취소
     */
    @Transactional
    public void cancel(final Long orderId) {
        Order order  = orderRepository.findById(orderId)
                .map(Order::cancel) // 주문 취소 하기
                .map(orderRepository::save) // 주문 취소 저장하기
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없음"));

        reservationProductStockRepository.findById(order.getProductId())
                .map(ReservationProductStock::addStockByOne) // 재고개수 + 1 해주기
                .map(reservationProductStockRepository::save) // 변경된 재고 수량 저장
                .orElseThrow(() -> new IllegalArgumentException("해당 상품 재고를 찾을 수 없음"));
    }
}
