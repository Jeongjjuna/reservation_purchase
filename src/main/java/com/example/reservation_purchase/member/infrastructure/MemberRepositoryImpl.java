package com.example.reservation_purchase.member.infrastructure;

import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.infrastructure.entity.MemberEntity;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public MemberRepositoryImpl(final MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public Member save(final Member member) {
        return memberJpaRepository.save(MemberEntity.from(member))
                .toModel();
    }
}
