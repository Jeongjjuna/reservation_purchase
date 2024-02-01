package com.example.activity_service.article.presentation;

import com.example.activity_service.article.application.ArticleReadService;
import com.example.activity_service.article.application.ArticleService;
import com.example.activity_service.article.domain.ArticleCreate;
import com.example.activity_service.article.presentation.response.ArticleResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * TODO : url을 어떻게 수정할 지 고민해보자. 다양한 사례들 찾아보기.
     */
    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody final ArticleCreate articleCreate,
            @RequestParam(name = "member", required = false) Long principalId
    ) {
        Long createdId = articleService.create(principalId, articleCreate);
        return ResponseEntity.created(URI.create("/v1/articles/" + createdId)).build();
    }

    /**
     * 내가 팔로우한 사람들의 게시글 조회
     */
    @GetMapping("/my-follows")
    public ResponseEntity<Page<ArticleResponse>> readMyFollowsArticles(
            @RequestParam(name = "member", required = false) Long principalId
    ) {
        Page<ArticleResponse> articleResponses = articleReadService.readMyFollowsArticles(principalId);
        return ResponseEntity.ok(articleResponses);
    }
}
