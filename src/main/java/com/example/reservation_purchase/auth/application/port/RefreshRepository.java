package com.example.reservation_purchase.auth.application.port;

import java.util.Optional;

public interface RefreshRepository {

    String save(Long memberId, String refreshToken);

    Optional<String> findByValue(String value);

    void delete(String value);

    Optional<String> findByMemberId(Long id);
}
