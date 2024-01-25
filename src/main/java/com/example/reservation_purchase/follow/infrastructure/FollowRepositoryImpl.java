package com.example.reservation_purchase.follow.infrastructure;

import com.example.reservation_purchase.follow.application.port.FollowRepository;
import com.example.reservation_purchase.follow.domain.Follow;
import com.example.reservation_purchase.follow.infrastructure.entity.FollowEntity;
import org.springframework.stereotype.Repository;

@Repository
public class FollowRepositoryImpl implements FollowRepository {

    private final FollowJpaRepository followJpaRepository;

    public FollowRepositoryImpl(final FollowJpaRepository followJpaRepository) {
        this.followJpaRepository = followJpaRepository;
    }

    @Override
    public Follow save(Follow follow) {
        return followJpaRepository.save(FollowEntity.from(follow)).toModel();
    }
}
