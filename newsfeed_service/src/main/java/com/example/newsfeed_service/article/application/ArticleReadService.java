package com.example.newsfeed_service.article.application;

import com.example.newsfeed_service.article.application.port.ArticleRepository;
import com.example.newsfeed_service.article.presentation.response.ArticleResponse;
import com.example.newsfeed_service.follow.application.port.FollowRepository;
import com.example.newsfeed_service.follow.domain.Follow;
import com.example.newsfeed_service.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleReadService {

    private final ArticleRepository articleRepository;
    private final FollowRepository followRepository;

    public ArticleReadService(
            final ArticleRepository articleRepository,
            final FollowRepository followRepository
    ) {
        this.articleRepository = articleRepository;
        this.followRepository = followRepository;
    }

    /**
     * 내가 팔로우한 사용자들의 게시글 목록을 불러온다.
     * TODO : 현재는 작성자 정보에 대해 id값만 반환 -> 추후 필요하면 작성자 정보도 응답에 추가해줘야함
     */
    public Page<ArticleResponse> readMyFollowsArticles(Long principalId) {

        // 내가 팔우한 사람들의 Ids
        List<Long> followingIds = followRepository.findFollowing(principalId).stream()
                .map(Follow::getFollowingMember)
                .map(Member::getId)
                .toList();

        Pageable pageable = PageRequest.of(
                0,
                20,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        return articleRepository.findByWriterIdIn(followingIds, pageable).map(ArticleResponse::from);
    }
}
