package com.example.reservation_purchase.member.application;

import com.example.reservation_purchase.exception.GlobalException;
import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.application.port.ProfileRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException.MemberNotFoundException;
import com.example.reservation_purchase.member.exception.MemberException.MemberUnauthorizedException;
import com.example.reservation_purchase.member.exception.MemberException.ProfileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ProfileService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public ProfileService(final MemberRepository memberRepository, final ProfileRepository profileRepository) {
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
    }

    /**
     * 프로필 이미지 업로드
     * 만약 이미 저장 되어있다면, 업데이트 한다.
     */
    @Transactional
    public String upload(final Long targetId, final Long principalId, final MultipartFile file) {

        checkAuthorized(targetId, principalId);
        checkNullProfile(file);

        Member member = findExistMember(targetId);

        String savedUrl = replaceProfile(member, file); // 로컬 DB 저장

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

    private Member findExistMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    private String replaceProfile(Member member, MultipartFile file) {
        if (member.isProfileUploaded()) {
            profileRepository.delete(member.getProfileUrl());
        }
        return profileRepository.upload(file);
    }

}
