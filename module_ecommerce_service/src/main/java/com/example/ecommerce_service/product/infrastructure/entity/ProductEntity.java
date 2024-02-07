package com.example.ecommerce_service.product.infrastructure.entity;

import com.example.ecommerce_service.product.domain.Product;
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
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "price", nullable = false)
    private Long price;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static ProductEntity from(final Product product) {
        final ProductEntity productEntity = new ProductEntity();
        productEntity.id = product.getId();
        productEntity.name = product.getName();
        productEntity.content = product.getContent();
        productEntity.price = product.getPrice();
        productEntity.createdAt = product.getCreatedAt();
        productEntity.updatedAt = product.getUpdatedAt();
        productEntity.deletedAt = product.getDeletedAt();
        return productEntity;
    }

    public Product toModel() {
        return Product.builder()
                .id(id)
                .name(name)
                .content(content)
                .price(price)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }
}
