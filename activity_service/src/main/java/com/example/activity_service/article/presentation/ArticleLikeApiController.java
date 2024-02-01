package com.example.activity_service.article.presentation;

import com.example.activity_service.article.application.ArticleLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ArticleLikeApiController {

    private final ArticleLikeService articleLikeService;

    public ArticleLikeApiController(final ArticleLikeService articleLikeService) {
        this.articleLikeService = articleLikeService;
    }

    /**
     * 게시글 좋아요 기능
     */
    @PostMapping("/articles/{id}/like")
    public ResponseEntity<Void> create(
            @PathVariable("id") final Long articleId,
            @RequestParam(name = "member", required = false) Long principalId
    ) {
        articleLikeService.like(articleId, principalId);
        return ResponseEntity.ok().build();
    }
}
