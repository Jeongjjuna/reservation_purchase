package com.example.activity_service.comment.presentation;

import com.example.activity_service.comment.application.CommentLikeService;
import com.example.activity_service.common.response.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/comments")
public class CommentLikeApiController {

    private final CommentLikeService commentLikeService;

    public CommentLikeApiController(final CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    @PostMapping("/{id}/like")
    public Response<Void> create(
            @PathVariable("id") final Long commentId,
            @RequestParam(name = "member", required = false) Long principalId
    ) {
        commentLikeService.like(commentId, principalId);
        return Response.success();
    }
}
