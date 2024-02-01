package com.example.activity_service.article.application;

import com.example.activity_service.article.application.port.ArticleRepository;
import com.example.activity_service.article.domain.Article;
import com.example.activity_service.article.domain.ArticleCreate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Transactional
    public Long create(Long principalId, ArticleCreate articleCreate) {

        Article article = Article.create(principalId, articleCreate);

        return articleRepository.save(article).getId();
    }
}
