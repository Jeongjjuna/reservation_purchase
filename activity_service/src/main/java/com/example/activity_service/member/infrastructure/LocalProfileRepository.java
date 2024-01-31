package com.example.activity_service.member.infrastructure;

import com.example.activity_service.member.application.port.ProfileRepository;
import com.example.activity_service.exception.GlobalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Repository
public class LocalProfileRepository implements ProfileRepository {

    @Value("${file.local.upload.path}")
    private String uploadPath;

    /**
     * 로컬 저장소에 이미지 저장
     */
    @Override
    public String upload(final MultipartFile file) {

        String uuid = UUID.randomUUID().toString();
        String originalName = file.getOriginalFilename();
        Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);

        try {
            file.transferTo(savePath);
        } catch (Exception e) {
            throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "profile Image save error");
        } finally {
            return savePath.getFileName().toString();
        }
    }

    /**
     * 로컬 저장소 이미지 삭제
     */
    @Override
    public void delete(String fileName) {
        Path filePath = Paths.get(uploadPath, fileName);

        try {
            Files.delete(filePath);
        } catch (Exception e) {
            throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "profile image delete error");
        }
    }
}
