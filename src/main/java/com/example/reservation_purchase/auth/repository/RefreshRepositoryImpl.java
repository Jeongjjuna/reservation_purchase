package com.example.reservation_purchase.auth.repository;

import com.example.reservation_purchase.auth.application.port.RefreshRepository;
import com.example.reservation_purchase.auth.repository.entity.RefreshTokenEntity;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class RefreshRepositoryImpl implements RefreshRepository {

    private final RefreshJapRepository refreshJapRepository;

    public RefreshRepositoryImpl(final RefreshJapRepository refreshJapRepository) {
        this.refreshJapRepository = refreshJapRepository;
    }

    @Override
    public String save(final Long memberId, final String refreshToken) {
        return refreshJapRepository.save(RefreshTokenEntity.from(memberId, refreshToken)).getValue();
    }

    @Override
    public Optional<String> findByValue(final String value) {
        return refreshJapRepository.findByValue(value).map(RefreshTokenEntity::getValue);
    }

    @Override
    public void delete(final String value) {
        refreshJapRepository.deleteByValue(value);
    }

    @Override
    public Optional<String> findByMemberId(final Long id) {
        return refreshJapRepository.findByMemberId(id).map(RefreshTokenEntity::getValue);
    }

}
