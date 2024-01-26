package com.example.reservation_purchase.newsfeed.presentation;

import com.example.reservation_purchase.auth.domain.UserDetailsImpl;
import com.example.reservation_purchase.newsfeed.application.NewsfeedService;
import com.example.reservation_purchase.newsfeed.domain.NewsfeedCreate;
import com.example.reservation_purchase.newsfeed.presentation.response.NewsfeedResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

/*
  추후에 내부 서버들에서 호출할 예정이다.
  ex) 유저서비스 -> 뉴스피드 api 호출
      게시글/좋아요서비스 -> 뉴스피드 api 호출
 */
@RestController
@RequestMapping("/api/newsfeeds")
public class NewsfeedApiController {

    private final NewsfeedService newsfeedService;

    public NewsfeedApiController(final NewsfeedService newsfeedService) {
        this.newsfeedService = newsfeedService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody final NewsfeedCreate newsfeedCreate) {
        newsfeedService.create(newsfeedCreate);
        return ResponseEntity.created(URI.create("api/newsfeeds/my")).build();
    }

    // 내가 팔로우한 모든 사용자의 뉴스피드를 보여준다.
    @GetMapping("/my")
    public ResponseEntity<Page<NewsfeedResponse>> create(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Page<NewsfeedResponse> myNewsfeeds = newsfeedService.my(userDetails.getId()).map(NewsfeedResponse::from);
        return ResponseEntity.ok(myNewsfeeds);
    }
}
