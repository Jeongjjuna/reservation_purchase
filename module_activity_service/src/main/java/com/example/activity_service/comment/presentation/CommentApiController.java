package com.example.activity_service.comment.presentation;

import com.example.activity_service.comment.application.CommentService;
import com.example.activity_service.comment.domain.CommentCreate;
import com.example.activity_service.common.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/comments")
public class CommentApiController {

    private final CommentService commentService;

    /**
     * 댓글 생성
     */
    @PostMapping
    public Response<Void> create(
            @RequestBody final CommentCreate commentCreate,
            @RequestParam(name = "member") final Long principalId
    ) {
        commentService.create(principalId, commentCreate);
        return Response.success();
    }
}
