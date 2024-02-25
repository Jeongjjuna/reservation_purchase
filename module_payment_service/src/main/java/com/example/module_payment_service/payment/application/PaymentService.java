package com.example.module_payment_service.payment.application;

import com.example.module_payment_service.payment.application.port.OrderServiceAdapter;
import com.example.module_payment_service.payment.application.port.PaymentRepository;
import com.example.module_payment_service.payment.application.port.StockServiceAdapter;
import com.example.module_payment_service.payment.domain.Order;
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

        double probability = Math.random();
        if (0.2 < probability) {
            // 1. 결제 정보 생성
            final Payment payment = Payment.create(paymentCreate);
            final Payment savedPayment = paymentRepository.save(payment);

            // 2. 외부 결제 시스템을 통한 결제 진행

            orderServiceAdapter.complete(payment.getOrderId());
            log.info("feign응답성공 : 주문서비스의 주문완료 요청");
            return "success";
        } else {
            Order order = orderServiceAdapter.findById(paymentCreate.getOrderId())
                    .map(Order::cancel)
                    .map(o -> orderServiceAdapter.cancel(o.getId()))
                    .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없음"));
            log.info("feign응답성공 : 주문서비스의 주문취소 요청");

            final Payment payment = Payment.create(paymentCreate);
            payment.cancel();
            final Payment savedPayment = paymentRepository.save(payment);

            return "failure";
        }
    }

}
