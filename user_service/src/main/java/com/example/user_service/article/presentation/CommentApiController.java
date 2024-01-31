package com.example.user_service.article.presentation;

import com.example.user_service.article.application.CommentService;
import com.example.user_service.article.domain.CommentCreate;
import com.example.user_service.auth.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequestMapping("/v1/comments")
public class CommentApiController {

    private final CommentService commentService;

    public CommentApiController(final CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 댓글 생성
     */
    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody final CommentCreate commentCreate,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        Long createdId = commentService.create(userDetails.getId(), commentCreate);
        return ResponseEntity.created(URI.create("/v1/comments/" + createdId)).build();
    }
}
