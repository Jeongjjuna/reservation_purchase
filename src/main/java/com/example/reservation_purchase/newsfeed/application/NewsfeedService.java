package com.example.reservation_purchase.newsfeed.application;

import com.example.reservation_purchase.follow.application.port.FollowRepository;
import com.example.reservation_purchase.follow.domain.Follow;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.newsfeed.application.port.NewsfeedRepository;
import com.example.reservation_purchase.newsfeed.domain.Newsfeed;
import com.example.reservation_purchase.newsfeed.domain.NewsfeedCreate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class NewsfeedService {

    private final NewsfeedRepository newsfeedRepository;
    private final FollowRepository followRepository;

    public NewsfeedService(final NewsfeedRepository newsfeedRepository, final FollowRepository followRepository) {
        this.newsfeedRepository = newsfeedRepository;
        this.followRepository = followRepository;
    }

    /**
     * 뉴스피드 생성
     * - 추후 특정 회원이 하는 동작에 따라 뉴스피드를 생성해주도록 내부 요청으로 사용된다.
     */
    @Transactional
    public void create(final NewsfeedCreate newsfeedCreate) {
        Newsfeed newsfeed = Newsfeed.create(newsfeedCreate);
        newsfeedRepository.save(newsfeed);
    }

    /**
     * 나의 뉴스피드 조회
     */
    public Page<Newsfeed> my(final Long principalId) {

        // TODO : 진짜 만약 팔로우를 걸어놓은 사람이 엄청 많다면? 만명이상?
        List<Long> followingIds = followRepository.findFollowing(principalId).stream()
                .map(Follow::getFollowingMember)
                .map(Member::getId)
                .toList();

        // TODO : 마지막으로 확인한 시점 이후로 최신 데이터를 가져온다.
        Pageable pageable = PageRequest.of(
                0,
                20,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        return newsfeedRepository.findAllByFollowingIds(followingIds, pageable);
    }
}
