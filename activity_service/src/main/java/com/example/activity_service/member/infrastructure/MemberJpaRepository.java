package com.example.activity_service.member.infrastructure;

import com.example.activity_service.member.infrastructure.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByEmail(String email);

    Page<MemberEntity> findAllByIdIn(List<Long> ids, Pageable pageable);

}
