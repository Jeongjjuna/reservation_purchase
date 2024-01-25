package com.example.reservation_purchase.member.infrastructure;

import com.example.reservation_purchase.member.infrastructure.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByEmail(String email);
}
