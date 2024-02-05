package com.example.user_service.member.infrastructure;

import com.example.user_service.member.application.port.MemberRepository;
import com.example.user_service.member.domain.Member;
import com.example.user_service.member.infrastructure.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Member> findById(final Long id) {
        return memberJpaRepository.findById(id).map(MemberEntity::toModel);
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        return memberJpaRepository.findByEmail(email).map(MemberEntity::toModel);
    }

    @Override
    public Page<Member> findAllByIdIn(final List<Long> ids, final Pageable pageable) {
        return memberJpaRepository.findAllByIdIn(ids, pageable).map(MemberEntity::toModel);
    }
}
