package com.example.module_order_service.order.application;

import com.example.module_order_service.common.exception.GlobalException;
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
import org.springframework.http.HttpStatus;
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

        checkReservationStartAt(orderCreate);

        final int orderProductPrice = requestOrderProduct(orderCreate).getPrice();

        final Order order = Order.create(orderCreate, orderProductPrice);
        orderRepository.save(order);

        final OrderHistory orderHistory = OrderHistory.create(order);
        orderHistoryRepository.save(orderHistory);

        requestSubtractStock(order);

        return order.getId();
    }

    /**
     * 고객 변심으로 결제 화면 창을 떠날 때 요청된다.
     * 주문 취소
     */
    @Transactional
    public Order cancel(final Long orderId) {

        final Order order = cancelOrderIfExist(orderId);  // TODO : 주문 엔티티를 가져올 때 deleted_at == null 인 값만 가져와야 한다.

        cancelOrderHistoryIfExist(orderId);

        requestAddStock(order);

        return order;
    }

    @Transactional
    public Order complete(final Long orderId) {
        final Order order = findExsistOrder(orderId);

        checkCanceled(order);

        completeOrderHistoryIfExist(orderId);

        return order;
    }

    private Order findExsistOrder(final Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 해당 주문을 찾을 수 없습니다."));
    }

    private void checkReservationStartAt(final OrderCreate orderCreate) {
        if (!productServiceAdapter.isAfterReservationStartAt(orderCreate.getProductId())) {
            throw new GlobalException(HttpStatus.CONFLICT, "[ERROR] 해당 상품의 예약 구매 오픈 시간 이전 입니다.");
        }
    }

    private OrderProduct requestOrderProduct(final OrderCreate orderCreate) {
        return productServiceAdapter.findOrderProductById(orderCreate.getProductId())
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 해당 상품 재고를 찾을 수 없습니다."));
    }

    private void requestSubtractStock(final Order order) {
        OrderStock orderStock = OrderStock.builder()
                .productId(order.getProductId())
                .stockCount(order.getQuantity())
                .build();
        stockServiceAdapter.subtractStock(order.getProductId(), orderStock);
    }

    private void requestAddStock(Order order) {
        OrderStock orderStock = OrderStock.builder()
                .productId(order.getProductId())
                .stockCount(order.getQuantity())
                .build();
        stockServiceAdapter.addStock(order.getProductId(), orderStock);
    }

    private OrderHistory cancelOrderHistoryIfExist(final Long orderId) {
        return orderHistoryRepository.findByOrderId(orderId)
                .map(OrderHistory::cancel)
                .map(orderHistoryRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 해당 주문기록을 찾을 수 없습니다."));
    }

    private Order cancelOrderIfExist(final Long orderId) {
        return orderRepository.findById(orderId)
                .map(Order::cancel)
                .map(orderRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 해당 주문을 찾을 수 없습니다."));
    }

    private OrderHistory completeOrderHistoryIfExist(final Long orderId) {
        return orderHistoryRepository.findByOrderId(orderId)
                .map(OrderHistory::complete)
                .map(orderHistoryRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 해당 주문기록을 찾을 수 없습니다."));
    }

    private void checkCanceled(final Order order) {
        if (order.isCanceled()) {
            throw new GlobalException(HttpStatus.CONFLICT, "[ERROR] 이미 삭제된 주문 입니다. 완료할 수 없습니다.");
        }
    }
}
