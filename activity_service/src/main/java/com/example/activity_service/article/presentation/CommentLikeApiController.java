package com.example.activity_service.article.presentation;

import com.example.activity_service.article.application.CommentLikeService;
import com.example.activity_service.auth.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class CommentLikeApiController {

    private final CommentLikeService commentLikeService;

    public CommentLikeApiController(final CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    @PostMapping("/comments/{id}/like")
    public ResponseEntity<Void> create(
            @PathVariable("id") final Long commentId,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        commentLikeService.like(commentId, userDetails.getId());
        return ResponseEntity.ok().build();
    }
}
