package com.example.reservation_purchase.article.infrastructure.entity;

import com.example.reservation_purchase.article.domain.ArticleLike;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "article_like")
public class ArticleLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private ArticleEntity articleEntity;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static ArticleLikeEntity from(final ArticleLike articleLike) {
        ArticleLikeEntity articleLikeEntity = new ArticleLikeEntity();
        articleLikeEntity.id = articleLike.getId();
        articleLikeEntity.articleEntity = ArticleEntity.from(articleLike.getArticle());
        articleLikeEntity.memberId = articleLike.getMemberId();
        articleLikeEntity.createdAt = articleLike.getCreatedAt();
        articleLikeEntity.deletedAt = articleLike.getDeletedAt();
        return articleLikeEntity;
    }

    public ArticleLike toModel() {
        return ArticleLike.builder()
                .id(id)
                .article(articleEntity.toModel())
                .memberId(memberId)
                .createdAt(createdAt)
                .deletedAt(deletedAt)
                .build();
    }
}
