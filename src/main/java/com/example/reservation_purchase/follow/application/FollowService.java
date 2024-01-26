package com.example.reservation_purchase.follow.application;

import com.example.reservation_purchase.follow.application.port.FollowRepository;
import com.example.reservation_purchase.follow.domain.Follow;
import com.example.reservation_purchase.follow.domain.FollowRequest;
import com.example.reservation_purchase.follow.exception.FollowErrorCode;
import com.example.reservation_purchase.follow.exception.FollowException.FollowUnauthorizedException;
import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException;
import com.example.reservation_purchase.newsfeed.application.NewsfeedService;
import com.example.reservation_purchase.newsfeed.domain.NewsfeedCreate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    private final NewsfeedService newsfeedService;

    public FollowService(final FollowRepository followRepository, final MemberRepository memberRepository, final NewsfeedService newsfeedService) {
        this.followRepository = followRepository;
        this.memberRepository = memberRepository;
        this.newsfeedService = newsfeedService;
    }

    /**
     * 팔로우 하기
     */
    @Transactional
    public void follow(final Long principalId, final FollowRequest followRequest) {

        // TODO : 이미 팔로우가 되어있다면 어떻게 처리할 것인가?
        if (!principalId.equals(followRequest.getFollowerMemberId())) {
            throw new FollowUnauthorizedException(FollowErrorCode.UNAUTHORIZED_ACCESS_ERROR);
        }

        // TODO : 추후에 데이터를 가져오지말고 있는지만 확인해보자.(최적화)
        Member followerMember = memberRepository.findById(followRequest.getFollowerMemberId())
                .orElseThrow(() -> new MemberException.MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));

        Member folloingMember = memberRepository.findById(followRequest.getFollowingMemberId())
                .orElseThrow(() -> new MemberException.MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));

        Follow follow = Follow.create(followerMember, folloingMember);
        followRepository.save(follow);

        /**
         * 추후에 서비스로 분리 후 RestTemplate 으로 호출한다.
         */
        NewsfeedCreate newsfeedCreate = NewsfeedCreate.builder()
                .receiverId(followRequest.getFollowingMemberId())
                .senderId(followRequest.getFollowerMemberId())
                .newsfeedType("follow")
                .build();
        newsfeedService.create(newsfeedCreate);
    }
}
