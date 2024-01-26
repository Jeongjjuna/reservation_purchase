package com.example.reservation_purchase.newsfeed.presentation;

import com.example.reservation_purchase.newsfeed.application.NewsfeedService;
import com.example.reservation_purchase.newsfeed.domain.NewsfeedCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
  추후에 내부 서버들에서 호출할 예정이다.
  ex) 유저서비스 -> 뉴스피드 api 호출
      게시글/좋아요서비스 -> 뉴스피드 api 호출
 */
@RestController
@RequestMapping("/api/newsfeed")
public class NewsfeedApiController {

    private final NewsfeedService newsfeedService;

    public NewsfeedApiController(final NewsfeedService newsfeedService) {
        this.newsfeedService = newsfeedService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody final NewsfeedCreate newsfeedCreate) {
        newsfeedService.create(newsfeedCreate);
        return ResponseEntity.ok().build();
    }
}
