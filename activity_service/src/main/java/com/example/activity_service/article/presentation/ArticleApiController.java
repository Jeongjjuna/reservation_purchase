package com.example.activity_service.article.presentation;

import com.example.activity_service.article.domain.ArticleCreate;
import com.example.activity_service.article.application.ArticleReadService;
import com.example.activity_service.article.application.ArticleService;
import com.example.activity_service.article.presentation.response.ArticleResponse;
import com.example.activity_service.auth.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequestMapping("/v1/articles")
public class ArticleApiController {

    private final ArticleService articleService;
    private final ArticleReadService articleReadService;

    public ArticleApiController(
            final ArticleService articleService,
            final ArticleReadService articleReadService
    ) {
        this.articleService = articleService;
        this.articleReadService = articleReadService;
    }

    /**
     * 게시글 생성
     */
    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody final ArticleCreate articleCreate,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        Long createdId = articleService.create(userDetails.getId(), articleCreate);
        return ResponseEntity.created(URI.create("/v1/articles/" + createdId)).build();
    }

    /**
     * 내가 팔로우한 사람들의 게시글 조회
     */
    @GetMapping("/my-follows")
    public ResponseEntity<Page<ArticleResponse>> readMyFollowsArticles(
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        Page<ArticleResponse> articleResponses = articleReadService.readMyFollowsArticles(userDetails.getId());
        return ResponseEntity.ok(articleResponses);
    }
}
