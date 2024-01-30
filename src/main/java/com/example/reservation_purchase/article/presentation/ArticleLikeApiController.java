package com.example.reservation_purchase.article.presentation;

import com.example.reservation_purchase.article.application.ArticleLikeService;
import com.example.reservation_purchase.auth.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        articleLikeService.like(articleId, userDetails.getId());
        return ResponseEntity.ok().build();
    }
}
