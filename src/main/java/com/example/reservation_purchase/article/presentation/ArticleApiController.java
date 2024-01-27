package com.example.reservation_purchase.article.presentation;

import com.example.reservation_purchase.article.application.ArticleService;
import com.example.reservation_purchase.article.domain.ArticleCreate;
import com.example.reservation_purchase.auth.domain.UserDetailsImpl;
import com.example.reservation_purchase.member.presentation.response.MemberJoinResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequestMapping("/api/articles")
public class ArticleApiController {

    private final ArticleService articleService;

    public ArticleApiController(final ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 게시글 생성
     */
    @PostMapping
    public ResponseEntity<MemberJoinResponse> create(
            @RequestBody final ArticleCreate articleCreate,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        Long createdId = articleService.create(userDetails.getId(), articleCreate);
        return ResponseEntity.created(URI.create("/api/articles/" + createdId)).build();
    }
}
