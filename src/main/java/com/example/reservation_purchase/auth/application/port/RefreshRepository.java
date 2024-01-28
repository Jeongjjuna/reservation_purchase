package com.example.reservation_purchase.auth.application.port;

import java.util.Map;

public interface RefreshRepository {

    String save(final String key, final String value, final long duration);

    String findByValue(String key);

    void delete(String key);

    void addToHash(String hashName, String key, String value);

    String getFromHash(String hashName, String key);

    Map<String, String> getAllFromHash(String hashName);

    void removeFromHash(String hashName, String key);
}
