package com.example.ecommerce_service.reservation_product.infrastructure.entity;

import com.example.ecommerce_service.reservation_product.domain.ReservationProduct;
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
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Entity
@Table(name = "reservation_product")
public class ReservationProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_product_id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "purchase_button_activation_at", nullable = false)
    private LocalDateTime purchaseButtonActivationAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static ReservationProductEntity from(final ReservationProduct reservationProduct) {
        final ReservationProductEntity reservationProductEntity = new ReservationProductEntity();
        reservationProductEntity.id = reservationProduct.getId();
        reservationProductEntity.name = reservationProduct.getName();
        reservationProductEntity.content = reservationProduct.getContent();
        reservationProductEntity.price = reservationProduct.getPrice();
        reservationProductEntity.purchaseButtonActivationAt = reservationProduct.getPurchaseButtonActivationAt();
        reservationProductEntity.createdAt = reservationProduct.getCreatedAt();
        reservationProductEntity.updatedAt = reservationProduct.getUpdatedAt();
        reservationProductEntity.deletedAt = reservationProduct.getDeletedAt();
        return reservationProductEntity;
    }

    public ReservationProduct toModel() {
        return ReservationProduct.builder()
                .id(id)
                .name(name)
                .content(content)
                .price(price)
                .purchaseButtonActivationAt(purchaseButtonActivationAt)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }
}
