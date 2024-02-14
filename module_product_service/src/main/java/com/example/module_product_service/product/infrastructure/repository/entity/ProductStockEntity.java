package com.example.module_product_service.product.infrastructure.repository.entity;

import com.example.module_product_service.product.domain.ProductStock;
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
public class ProductStockEntity {

    @Id
    @Column(name = "product_id", updatable = false)
    private Long productId;

    @Column(name = "stock_count", nullable = false)
    private Integer stockCount;

    public static ProductStockEntity from(final ProductStock productStock) {
        final ProductStockEntity productStockEntity = new ProductStockEntity();
        productStockEntity.productId = productStock.getProductId();
        productStockEntity.stockCount = productStock.getStockCount();
        return productStockEntity;
    }

    public ProductStock toModel() {
        return ProductStock.builder()
                .productId(productId)
                .stockCount(stockCount)
                .build();
    }
}
