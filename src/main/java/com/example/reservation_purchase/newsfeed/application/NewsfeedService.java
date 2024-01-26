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

    @Transactional
    public void create(final NewsfeedCreate newsfeedCreate) {
        Newsfeed newsfeed = Newsfeed.create(newsfeedCreate);
        newsfeedRepository.save(newsfeed);
    }

    public Page<Newsfeed> my(final Long principalId) {

        /*
         1. 내가 팔로우한 모든 사용자 id를 가져온다.
         TODO : 진짜 만약 팔로우를 걸어놓은 사람이 엄청 많다면? 만명이상?
        */
        List<Long> followingIds = followRepository.findByFollowingMember(principalId).stream()
                .map(Follow::getFollowingMember)
                .map(Member::getId)
                .toList();

        /*
         2. 사용자 id에 따른 모든 뉴스피드를 최신순으로 가져온다.
            가장 최신의 피드 20개만 보여준다.
            TODO : 만약 확인하지 않은 뉴스피드만 가져오고 싶다면? viewed 필드를 활용해보자.
        */
        Pageable pageable = PageRequest.of(
                0, 20, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Newsfeed> ness = newsfeedRepository.findAllByFollowingIds(followingIds, pageable);
        return ness;
    }
}
