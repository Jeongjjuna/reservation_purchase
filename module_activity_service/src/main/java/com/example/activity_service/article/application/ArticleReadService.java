package com.example.activity_service.article.application;

import com.example.activity_service.article.application.port.ArticleRepository;
import com.example.activity_service.article.presentation.response.ArticleResponse;
import com.example.activity_service.follow.application.port.FollowRepository;
import com.example.activity_service.follow.domain.Follow;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class ArticleReadService {

    private final ArticleRepository articleRepository;
    private final FollowRepository followRepository;

    /**
     * 내가 팔로우한 사용자들의 게시글 목록을 불러온다.
     */
    public Page<ArticleResponse> readMyFollowsArticles(Long principalId) {

        List<Long> myFollowingIds = followRepository.findFollowing(principalId).stream()
                .map(Follow::getFollowingMemberId)
                .toList();

        Pageable pageable = PageRequest.of(0, 20,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        return articleRepository.findByWriterIdIn(myFollowingIds, pageable).map(ArticleResponse::from);
    }
}
