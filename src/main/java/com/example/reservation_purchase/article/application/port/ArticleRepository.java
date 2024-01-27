package com.example.reservation_purchase.article.application.port;

import com.example.reservation_purchase.article.domain.Article;

public interface ArticleRepository {

    Article save(Article article);
}
