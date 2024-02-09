package com.example.ecommerce_service.payment.infrastructure.entity;

import com.example.ecommerce_service.payment.domain.Payment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Entity
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", updatable = false)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static PaymentEntity from(final Payment payment) {
        final PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.id = payment.getId();
        paymentEntity.orderId = payment.getOrderId();
        paymentEntity.memberId = payment.getMemberId();
        paymentEntity.createdAt = payment.getCreatedAt();
        paymentEntity.deletedAt = payment.getDeletedAt();
        return paymentEntity;
    }

    public Payment toModel() {
        return Payment.builder()
                .id(id)
                .orderId(orderId)
                .memberId(memberId)
                .createdAt(createdAt)
                .deletedAt(deletedAt)
                .build();
    }
}
