package com.example.user_service.member.application.port;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileRepository {

    String upload(MultipartFile file);

    void delete(String fileName);
}
