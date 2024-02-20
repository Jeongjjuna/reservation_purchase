package com.example.module_order_service.order.application;

import com.example.module_order_service.order.application.port.OrderHistoryRepository;
import com.example.module_order_service.order.application.port.OrderRepository;
import com.example.module_order_service.order.application.port.ProductServiceAdapter;
import com.example.module_order_service.order.application.port.StockServiceAdapter;
import com.example.module_order_service.order.domain.Order;
import com.example.module_order_service.order.domain.OrderCreate;
import com.example.module_order_service.order.domain.OrderHistory;
import com.example.module_order_service.order.domain.OrderProduct;
import com.example.module_order_service.order.domain.OrderStock;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final ProductServiceAdapter productServiceAdapter;
    private final StockServiceAdapter stockServiceAdapter;

    /**
     * 결제 화면 들어가기 버튼 클릭 시 주문 생성이 요청된다.
     * 주문 생성
     */
    @Transactional
    public Long create(final OrderCreate orderCreate) {

        // 1. 해당 상품이 존재하는가?
        final OrderProduct orderProduct = productServiceAdapter.findOrderProductById(orderCreate.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품 재고를 찾을 수 없음"));
        log.info("feign응답성공 : 상품서비스의 상품조회 요청");


        // 2. 주문 생성
        final Order order = Order.create(orderCreate, orderProduct.getPrice());
        final Order savedOrder = orderRepository.save(order);

        // 3. 주문 기록 생성
        final OrderHistory orderHistory = OrderHistory.create(savedOrder);
        orderHistoryRepository.save(orderHistory);

        // 4. 재고 서비스 서버에 재고 감소 요청(동기 feign)
        OrderStock orderStock = OrderStock.builder()
                .productId(order.getProductId())
                .stockCount(order.getQuantity())
                .build();
        stockServiceAdapter.subtract(order.getProductId(), orderStock);
        log.info("feign응답성공 : 재고서비스의 재고감소 요청");


        return savedOrder.getId();
    }

    /**
     * 고객 변심으로 결제 화면 창을 떠날 때 요청된다.
     * 주문 취소
     */
    @Transactional
    public Order cancel(final Long orderId) {

        // TODO : 애초에 주문 엔티티를 가져올 때 deleted_at == null 인 값만 가져와야 한다.
        // 1. 해당주문이 존재하는가? 존재하면 주문 취소
        final Order order  = orderRepository.findById(orderId)
                .map(Order::cancel)
                .map(orderRepository::save)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없음"));

        // 2. 해당 주문 기록이 존재하는가? 존재하면 주문기록 취소
        final OrderHistory orderHistory = orderHistoryRepository.findByOrderId(order.getId())
                .map(OrderHistory::cancel)
                .map(orderHistoryRepository::save)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문기록을 찾을 수 없음"));

        // 3. 재고 서비스 서버에 재고 증가 요청(동기 feign)
        OrderStock orderStock = OrderStock.builder()
                .productId(order.getProductId())
                .stockCount(order.getQuantity())
                .build();
        stockServiceAdapter.addStock(order.getProductId(), orderStock);
        log.info("feign응답성공 : 재고서비스의 재고증가 요청");

        return order;
    }

    public Order complete(final Long orderId) {
        final Order order  = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없음"));

        final OrderHistory orderHistory = orderHistoryRepository.findByOrderId(orderId)
                .map(OrderHistory::complete)
                .map(orderHistoryRepository::save)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문기록을 찾을 수 없음"));

        return order;
    }
}
