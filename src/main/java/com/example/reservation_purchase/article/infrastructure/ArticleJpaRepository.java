package com.example.reservation_purchase.article.infrastructure;

import com.example.reservation_purchase.article.infrastructure.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArticleJpaRepository extends JpaRepository<ArticleEntity, Long> {
    Page<ArticleEntity> findByWriterIdIn(List<Long> followingIds, Pageable pageable);
}
