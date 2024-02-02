package com.example.activity_service.comment.presentation;

import com.example.activity_service.comment.application.CommentService;
import com.example.activity_service.comment.domain.CommentCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            @RequestParam(name = "member", required = false) Long principalId
    ) {
        Long createdId = commentService.create(principalId, commentCreate);
        return ResponseEntity.created(URI.create("/v1/comments/" + createdId)).build();
    }
}
