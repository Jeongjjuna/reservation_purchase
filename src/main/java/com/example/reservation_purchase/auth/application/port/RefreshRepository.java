package com.example.reservation_purchase.auth.application.port;

public interface RefreshRepository {

    String save(final String refreshToken, final Long memberId, final long duration);

    String findByValue(String key);

    void delete(String key);

}
