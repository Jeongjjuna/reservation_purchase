package com.example.activity_service.article.presentation;

import com.example.activity_service.article.application.ArticleLikeService;
import com.example.activity_service.common.response.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/articles")
public class ArticleLikeApiController {

    private final ArticleLikeService articleLikeService;

    public ArticleLikeApiController(final ArticleLikeService articleLikeService) {
        this.articleLikeService = articleLikeService;
    }

    /**
     * 게시글 좋아요 기능
     */
    @PostMapping("/{id}/like")
    public Response<Void> create(
            @PathVariable("id") final Long articleId,
            @RequestParam(name = "member", required = false) Long principalId
    ) {
        articleLikeService.like(articleId, principalId);
        return Response.success();
    }
}
