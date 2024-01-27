package com.example.reservation_purchase.follow.application;

import com.example.reservation_purchase.follow.application.port.FollowRepository;
import com.example.reservation_purchase.follow.domain.Follow;
import com.example.reservation_purchase.follow.domain.FollowCreate;
import com.example.reservation_purchase.follow.exception.FollowErrorCode;
import com.example.reservation_purchase.follow.exception.FollowException.FollowDuplicatedException;
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
    public void follow(final Long principalId, final FollowCreate followCreate) {

        Member followerMember = findExistMember(followCreate.getFollowerMemberId());
        Member folloingMember = findExistMember(followCreate.getFollowingMemberId());

        checkAuthorized(followCreate.getFollowerMemberId(), principalId);
        checkDuplicated(followCreate);

        Follow follow = Follow.create(followerMember, folloingMember);
        Follow saved = followRepository.save(follow);

        /**
         * 뉴스피드에 팔로우 기록 추가
         * 추후에 서비스로 분리 후 RestTemplate 으로 호출한다.
         */
        NewsfeedCreate newsfeedCreate = NewsfeedCreate.builder()
                .receiverId(followCreate.getFollowingMemberId())
                .senderId(followCreate.getFollowerMemberId())
                .newsfeedType("follow")
                .activityId(saved.getId())
                .build();
        newsfeedService.create(newsfeedCreate);
    }

    private void checkDuplicated(final FollowCreate followCreate) {
        Long followerMemberId = followCreate.getFollowerMemberId();
        Long followingMemberId = followCreate.getFollowingMemberId();

        followRepository.findByFollowerAndFollowing(followerMemberId, followingMemberId).ifPresent(it -> {
            throw new FollowDuplicatedException(FollowErrorCode.FOLLOW_DUPLICATED);
        });
    }

    private void checkAuthorized(Long targetId, Long principalId) {
        if (!principalId.equals(targetId)) {
            throw new FollowUnauthorizedException(FollowErrorCode.UNAUTHORIZED_ACCESS_ERROR);
        }
    }

    private Member findExistMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new MemberException.MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
