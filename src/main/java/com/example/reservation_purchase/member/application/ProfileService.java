package com.example.reservation_purchase.member.application;

import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.application.port.ProfileRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public ProfileService(final MemberRepository memberRepository, final ProfileRepository profileRepository) {
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
    }

    /*
      TODO : 파일 업로드 트랜잭션 고민해야함
     */
    @Transactional
    public String upload(final Long memberId, final Long principalId, final MultipartFile file) {

        if (!memberId.equals(principalId)) {
            throw new IllegalArgumentException("본인 것만 프로필 설정 가능");
        }

        if (file == null) {
            throw new IllegalArgumentException("설정할 파일이 없습니다.");
        }

        Member member = memberRepository.findById(principalId).orElseThrow(() ->
                new MemberException.MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 이미 등록된 프로필 이미지가 있다면, 삭제한다.
        if (member.isProfileUploaded()) {
            profileRepository.delete(member.getProfileUrl());
        }

        // 새로운 프로필 이미지 등록
        String savedUrl = profileRepository.upload(file);

        // 등록된 경로 저장
        member.saveProfile(savedUrl);
        memberRepository.save(member);

        return savedUrl;
    }
}
