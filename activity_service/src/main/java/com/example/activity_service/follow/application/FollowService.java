package com.example.activity_service.follow.application;

import com.example.activity_service.client.NewsfeedClient;
import com.example.activity_service.follow.application.port.FollowRepository;
import com.example.activity_service.follow.domain.Follow;
import com.example.activity_service.follow.domain.FollowCreate;
import com.example.activity_service.follow.domain.FollowNewsfeed;
import com.example.activity_service.follow.exception.FollowErrorCode;
import com.example.activity_service.follow.exception.FollowException.FollowDuplicatedException;
import com.example.activity_service.follow.exception.FollowException.FollowUnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final NewsfeedClient newsfeedClient;

    public FollowService(
            final FollowRepository followRepository,
            final NewsfeedClient newsfeedClient
    ) {
        this.followRepository = followRepository;
        this.newsfeedClient = newsfeedClient;
    }

    /**
     * 팔로우 하기
     */
    @Transactional
    public void follow(final Long principalId, final FollowCreate followCreate) {

        // TODO : member 존재여부를 미리 인증 GATEWAY에서 받아야 하는가?
        // 만약 user_serivce에게 해당 유저가 존재하는지 확인 요청을 보내야 한다면
        // 아래에 user_service에 존재 하는지 요청하는 로직을 추가한다.(여러가지 근거들로 장/단점 따져봐야함)
        Long followerMemberId = followCreate.getFollowerMemberId();
        Long followingMemberId = followCreate.getFollowingMemberId();

        checkAuthorized(followCreate.getFollowerMemberId(), principalId);
        checkDuplicated(followCreate);

        Follow follow = Follow.create(followerMemberId, followingMemberId);
        Follow saved = followRepository.save(follow);

        /**
         * 뉴스피드에 좋아요 기록 추가
         * TODO : 1. 분산 트랜잭션 체크 2. 테스트할때 mongodb 트랜잭션 체크
         */
        FollowNewsfeed followNewsfeed = FollowNewsfeed.builder()
                .receiverId(followerMemberId)
                .senderId(followingMemberId)
                .newsfeedType("follow")
                .activityId(saved.getId())
                .build();
        newsfeedClient.create(followNewsfeed);
    }


    public List<Long> findByFollowerId(final Long principalId) {
        log.info("[내부 internal 요청] FollowService findByFollowerId() : 특정 회원이 팔로우한 모든 회원ID조회");
        return followRepository.findFollowing(principalId).stream()
                .map(Follow::getFollowingMemberId)
                .toList();
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
}
