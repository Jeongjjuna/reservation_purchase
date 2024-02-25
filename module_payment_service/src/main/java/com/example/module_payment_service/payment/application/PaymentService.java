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

        final Order order = orderServiceAdapter.findById(paymentCreate.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없음"));

        final Payment payment = Payment.create(paymentCreate);

        // 2. 외부 결제 시스템을 통한 결제 진행
        double externalPaymentResult = Math.random();
        if (0.2 < externalPaymentResult) {

            paymentRepository.save(payment);

            orderServiceAdapter.complete(payment.getOrderId());
            log.info("feign응답성공 : 주문서비스의 주문완료 요청");

            return "success";
        }

        payment.cancel();
        paymentRepository.save(payment);

        orderServiceAdapter.cancel(order.getId());
        log.info("feign응답성공 : 주문서비스의 주문취소 요청");

        return "failure";

    }
}
