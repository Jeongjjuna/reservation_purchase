package com.example.activity_service.follow.infrastructure;

import com.example.activity_service.follow.domain.Follow;
import com.example.activity_service.follow.application.port.FollowRepository;
import com.example.activity_service.follow.infrastructure.entity.FollowEntity;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FollowRepositoryImpl implements FollowRepository {

    private final FollowJpaRepository followJpaRepository;

    public FollowRepositoryImpl(final FollowJpaRepository followJpaRepository) {
        this.followJpaRepository = followJpaRepository;
    }

    @Override
    public Follow save(final Follow follow) {
        return followJpaRepository.save(FollowEntity.from(follow)).toModel();
    }

    @Override
    public List<Follow> findFollowing(final Long myId) {
        return followJpaRepository.findFollowing(myId).stream()
                .map(FollowEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Follow> findFollower(final Long myId) {
        return followJpaRepository.findFollower(myId).stream()
                .map(FollowEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Follow> findByFollowerAndFollowing(final Long followerMemberId, final Long followingMemberId) {
        return followJpaRepository.findByFollowerAndFollowing(followerMemberId, followingMemberId).map(FollowEntity::toModel);
    }

}
