package com.example.module_product_service.product.infrastructure.repository.entity;

import com.example.module_product_service.product.domain.ReservationTime;
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
@Table(name = "reservation_time")
public class ReservationTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_time_number", updatable = false)
    private Long id;

    @Column(name = "product_number", nullable = false)
    private Long productId;

    @Column(name = "reservation_start_at", nullable = false)
    private LocalDateTime reservationStartAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static ReservationTimeEntity from(final ReservationTime reservationTime) {
        final ReservationTimeEntity reservationTimeEntity = new ReservationTimeEntity();
        reservationTimeEntity.id = reservationTime.getId();
        reservationTimeEntity.productId = reservationTime.getProductId();
        reservationTimeEntity.createdAt = reservationTime.getCreatedAt();
        reservationTimeEntity.reservationStartAt = reservationTime.getReservationStartAt();
        return reservationTimeEntity;
    }

    public ReservationTime toModel() {
        return ReservationTime.builder()
                .id(id)
                .productId(productId)
                .createdAt(createdAt)
                .reservationStartAt(reservationStartAt)
                .build();
    }
}
