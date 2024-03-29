package com.example.user_service.member.application;

import com.example.user_service.common.exception.GlobalException;
import com.example.user_service.member.application.port.MemberRepository;
import com.example.user_service.member.application.port.ProfileRepository;
import com.example.user_service.member.domain.Member;
import com.example.user_service.member.exception.MemberErrorCode;
import com.example.user_service.member.exception.MemberException.MemberNotFoundException;
import com.example.user_service.member.exception.MemberException.MemberUnauthorizedException;
import com.example.user_service.member.exception.MemberException.ProfileNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
@Slf4j
public class ProfileService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    /**
     * 프로필 이미지 업로드
     * - 만약 이미 저장 되어있다면, 업데이트 한다.
     */
    @Transactional
    public String upload(final Long targetId, final Long principalId, final MultipartFile file) {

        checkAuthorized(targetId, principalId);
        checkNullProfile(file);

        final Member member = findExistMember(targetId);

        final String savedUrl = replaceProfile(member, file); // 로컬 DB 저장

        try {
            member.saveProfile(savedUrl);
            memberRepository.save(member);
        } catch (Exception e) {
            profileRepository.delete(member.getProfileUrl()); // CHECK : 여기서 또 에러나면? -> 별도의 후처리 검사
            log.error("local image db rollback");
            throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "profile Image save error");
        }
        return savedUrl;
    }

    private void checkNullProfile(final MultipartFile file) {
        if (file == null) {
            throw new ProfileNotFoundException(MemberErrorCode.PROFILE_NOT_FOUND);
        }
    }

    private void checkAuthorized(final Long targetId, final Long principalId) {
        if (!targetId.equals(principalId)) {
            throw new MemberUnauthorizedException(MemberErrorCode.UNAUTHORIZED_ACCESS_ERROR);
        }
    }

    private Member findExistMember(final Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    private String replaceProfile(final Member member, final MultipartFile file) {
        if (member.isProfileUploaded()) {
            profileRepository.delete(member.getProfileUrl());
        }
        return profileRepository.upload(file);
    }

}
