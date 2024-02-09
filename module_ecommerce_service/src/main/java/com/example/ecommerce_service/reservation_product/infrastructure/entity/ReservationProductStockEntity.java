package com.example.ecommerce_service.reservation_product.infrastructure.entity;

import com.example.ecommerce_service.reservation_product.domain.ReservationProductStock;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Entity
@Table(name = "reservation_product_stock")
public class ReservationProductStockEntity {

    @Id
    @Column(name = "reservation_product_id", updatable = false)
    private Long productId;

    @Column(name = "stock_count", nullable = false)
    private Integer stockCount;

    public static ReservationProductStockEntity from(final ReservationProductStock reservationProductStock) {
        final ReservationProductStockEntity reservationProductStockEntity = new ReservationProductStockEntity();
        reservationProductStockEntity.productId = reservationProductStock.getProductId();
        reservationProductStockEntity.stockCount = reservationProductStock.getStockCount();
        return reservationProductStockEntity;
    }

    public ReservationProductStock toModel() {
        return ReservationProductStock.builder()
                .productId(productId)
                .stockCount(stockCount)
                .build();
    }
}
