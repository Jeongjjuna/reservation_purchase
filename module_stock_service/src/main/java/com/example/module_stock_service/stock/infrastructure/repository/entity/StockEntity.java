package com.example.module_stock_service.stock.infrastructure.repository.entity;

import com.example.module_stock_service.stock.domain.Stock;
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

    public static StockEntity from(final Stock productStock) {
        final StockEntity productStockEntity = new StockEntity();
        productStockEntity.productId = productStock.getProductId();
        productStockEntity.stockCount = productStock.getStockCount();
        return productStockEntity;
    }

    public Stock toModel() {
        return Stock.builder()
                .productId(productId)
                .stockCount(stockCount)
                .build();
    }
}