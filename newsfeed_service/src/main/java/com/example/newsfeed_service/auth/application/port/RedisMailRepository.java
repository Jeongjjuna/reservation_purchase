package com.example.newsfeed_service.auth.application.port;

public interface RedisMailRepository {

    void setDataExpire(String email, String authenticNumber, long duration);

    String getData(String key);

    void deleteData(String key);
}
