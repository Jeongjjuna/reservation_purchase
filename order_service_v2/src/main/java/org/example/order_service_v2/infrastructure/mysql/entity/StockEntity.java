package org.example.order_service_v2.infrastructure.mysql.entity;

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
@Table(name = "product_stock")
public class StockEntity {

    @Id
    @Column(name = "product_number", updatable = false)
    private Long productId;

    @Column(name = "stock_count", nullable = false)
    private Integer stockCount;

}