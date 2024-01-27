package com.example.reservation_purchase.article.presentation.response;

import com.example.reservation_purchase.article.domain.Article;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ArticleResponse {

    private Long id;
    private Long writerId;
    private String content;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    @Builder
    public ArticleResponse(
            final Long id,
            final Long writerId,
            final String content,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        this.id = id;
        this.writerId = writerId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ArticleResponse from(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .writerId(article.getWriterId())
                .content(article.getContent())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
    }

}
