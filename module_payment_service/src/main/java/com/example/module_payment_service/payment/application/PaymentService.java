package com.example.module_payment_service.payment.application;

import com.example.module_payment_service.payment.application.port.OrderServiceAdapter;
import com.example.module_payment_service.payment.application.port.PaymentRepository;
import com.example.module_payment_service.payment.application.port.StockServiceAdapter;
import com.example.module_payment_service.payment.domain.Order;
import com.example.module_payment_service.payment.domain.OrderStock;
import com.example.module_payment_service.payment.domain.Payment;
import com.example.module_payment_service.payment.domain.PaymentCreate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderServiceAdapter orderServiceAdapter;
    private final StockServiceAdapter stockServiceAdapter;

    @Transactional
    public String create(final PaymentCreate paymentCreate) {

        final Payment payment = Payment.create(paymentCreate);
        final Payment savedPayment = paymentRepository.save(payment);

        //  외부 결제 시스템을 통한 결제 진행

        // 80 퍼 확률로 성공
        double probability = Math.random();
        if (0.2 < probability) {
            orderServiceAdapter.complete(payment.getOrderId());
            log.info("feign응답성공 : 주문서비스의 주문완료 요청");
            return "success";
        }

        /**
         * 실패 프로세스 정의
         */
        // TODO : 현재는 feign사용 -> redis, 비동기이벤트로 개선 예정
        Order order = orderServiceAdapter.findById(payment.getOrderId())
                .map(Order::cancel)
                .map(o -> orderServiceAdapter.cancel(o.getId()))
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없음"));
        log.info("feign응답성공 : 주문서비스의 주문취소 요청");


        savedPayment.cancel();
        paymentRepository.save(savedPayment);

        // 재고 서비스에 수량 증가 요청
        OrderStock orderStock = OrderStock.builder()
                .productId(order.getProductId())
                .stockCount(order.getQuantity())
                .build();
        stockServiceAdapter.addStock(order.getProductId(), orderStock);
        log.info("feign응답성공 : 재고서비스의 재고증가 요청");


        return "failure";
    }
}
