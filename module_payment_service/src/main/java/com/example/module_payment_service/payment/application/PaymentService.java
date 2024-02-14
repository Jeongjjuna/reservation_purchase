package com.example.module_payment_service.payment.application;

import com.example.module_payment_service.payment.application.port.OrderAdapter;
import com.example.module_payment_service.payment.application.port.PaymentRepository;
import com.example.module_payment_service.payment.application.port.ReservationProductStockAdapter;
import com.example.module_payment_service.payment.domain.Order;
import com.example.module_payment_service.payment.domain.Payment;
import com.example.module_payment_service.payment.domain.PaymentCreate;
import com.example.module_payment_service.payment.domain.ReservationProductStock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
     private final OrderAdapter orderAdapter;
     private final ReservationProductStockAdapter reservationProductStockAdapter;

    @Transactional
    public String create(final PaymentCreate paymentCreate) {

        final Payment payment = Payment.create(paymentCreate);
        final Payment savedPayment = paymentRepository.save(payment);

        //  외부 결제 시스템을 통한 결제 진행

        // 80 퍼 확률로 성공
        double probability = Math.random();
        if (0.2 < probability) {
            return "success";
        }

        /**
         * 실패 프로세스 정의
         */
        // TODO : 현재는 feign사용 -> redis, 비동기이벤트로 개선 예정
        Order order = orderAdapter.findById(payment.getOrderId())
                .map(Order::cancel)
                .map(o -> orderAdapter.cancel(o.getId()))
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없음"));

        // TODO : 현재는 feign사용 -> redis, 비동기이벤트로 개선 예정
        // TODO : 원자적으로 재고수량 변경 처리해야 함
        reservationProductStockAdapter.findById(order.getProductId())
                .map(ReservationProductStock::addStockByOne) // 재고개수 + 1 해주기
                .map(reservationProductStockAdapter::update)
                .orElseThrow(() -> new IllegalArgumentException("해당 재고를 찾을 수 없음"));

        savedPayment.cancel();
        paymentRepository.save(savedPayment);
        return "failure";
    }
}
