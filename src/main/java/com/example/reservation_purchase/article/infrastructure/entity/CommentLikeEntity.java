package com.example.reservation_purchase.article.infrastructure.entity;

import com.example.reservation_purchase.article.domain.CommentLike;
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
@Table(name = "comment_like")
public class CommentLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private CommentEntity commentEntity;

    @Column(name = "writer_id", nullable = false)
    private Long memberId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static CommentLikeEntity from(final CommentLike commentLike) {
        CommentLikeEntity commentLikeEntity = new CommentLikeEntity();
        commentLikeEntity.id = commentLike.getId();
        commentLikeEntity.commentEntity = CommentEntity.from(commentLike.getComment());
        commentLikeEntity.memberId = commentLike.getMemberId();
        commentLikeEntity.createdAt = commentLike.getCreatedAt();
        commentLikeEntity.deletedAt = commentLike.getDeletedAt();
        return commentLikeEntity;
    }

    public CommentLike toModel() {
        return CommentLike.builder()
                .id(id)
                .comment(commentEntity.toModel())
                .memberId(memberId)
                .createdAt(createdAt)
                .deletedAt(deletedAt)
                .build();
    }
}
