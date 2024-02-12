package com.example.ecommerce_service.payment.application;

import com.example.ecommerce_service.order.application.port.OrderRepository;
import com.example.ecommerce_service.order.domain.Order;
import com.example.ecommerce_service.payment.application.port.PaymentRepository;
import com.example.ecommerce_service.payment.domain.Payment;
import com.example.ecommerce_service.payment.domain.PaymentCreate;
import com.example.ecommerce_service.reservation_product.application.port.ReservationProductStockRepository;
import com.example.ecommerce_service.reservation_product.domain.ReservationProductStock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final ReservationProductStockRepository reservationProductStockRepository;

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

        // 실패 프로세스 진행
        Order order = orderRepository.findById(payment.getOrderId())
                .map(Order::cancel)
                .map(orderRepository::save)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없음"));

        reservationProductStockRepository.findById(order.getProductId())
                .map(ReservationProductStock::addStockByOne) // 재고개수 + 1 해주기
                .map(reservationProductStockRepository::save)
                .orElseThrow(() -> new IllegalArgumentException("해당 재고를 찾을 수 없음"));

        savedPayment.cancel();
        paymentRepository.save(savedPayment);
        return "failure";
    }

    private Optional<ReservationProductStock> findStockByOrder(Order order) {
        return reservationProductStockRepository.findById(order.getProductId());
    }

}
