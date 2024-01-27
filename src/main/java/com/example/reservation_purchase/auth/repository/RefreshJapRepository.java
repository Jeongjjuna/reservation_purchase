package com.example.reservation_purchase.auth.repository;

import com.example.reservation_purchase.auth.repository.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefreshJapRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByValue(String value);

    void deleteByValue(String value);

    Optional<RefreshTokenEntity> findByMemberId(Long id);
}
