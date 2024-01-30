package com.example.reservation_purchase.article.infrastructure.entity;

import com.example.reservation_purchase.article.domain.Comment;
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
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Entity
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private ArticleEntity articleEntity;

    @Column(name = "member_id", nullable = false)
    private Long writerId;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;

    public static CommentEntity from(final Comment comment) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.id = comment.getId();
        commentEntity.articleEntity = ArticleEntity.from(comment.getArticle());
        commentEntity.writerId = comment.getWriterId();
        commentEntity.content = comment.getContent();
        commentEntity.createdAt = comment.getCreatedAt();
        commentEntity.updatedAt = comment.getUpdatedAt();
        commentEntity.deletedAt = comment.getDeletedAt();
        return commentEntity;
    }

    public Comment toModel() {
        return Comment.builder()
                .id(id)
                .article(articleEntity.toModel())
                .writerId(writerId)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }
}
