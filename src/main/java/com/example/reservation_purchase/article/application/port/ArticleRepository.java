package com.example.reservation_purchase.article.application.port;

import com.example.reservation_purchase.article.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    Page<Article> findByWriterIdIn(List<Long> followingIds, Pageable pageable);

    Optional<Article> findById(Long articleId);
}
