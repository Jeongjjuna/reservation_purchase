package com.example.reservation_purchase.member.application;

import com.example.reservation_purchase.auth.application.port.RefreshRepository;
import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.domain.MemberUpdate;
import com.example.reservation_purchase.member.domain.PasswordUpdate;
import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException.MemberNotFoundException;
import com.example.reservation_purchase.member.exception.MemberException.MemberUnauthorizedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service
public class MemberUpdateService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RefreshRepository refreshRepository;

    public MemberUpdateService(final MemberRepository memberRepository, final BCryptPasswordEncoder passwordEncoder, final RefreshRepository refreshRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshRepository = refreshRepository;
    }

    /**
     * 이름, 인사말 업데이트
     */
    @Transactional
    public void update(final MemberUpdate memberUpdate, final Long targetId, final Long principalId) {

        checkAuthorized(targetId, principalId);

        Member member = findExistMember(targetId);

        member.update(memberUpdate);

        memberRepository.save(member);
    }

    /**
     * 비밀번호 업데이트
     */
    @Transactional
    public void updatePassword(
            final PasswordUpdate passwordUpdate,
            final Long targetId,
            final Long principalId
    ) {

        checkAuthorized(targetId, principalId);

        Member member = findExistMember(targetId);

        passwordUpdate.validate();

        String encodedPassword = passwordEncoder.encode(passwordUpdate.getPassword());
        member.applyEncodedPassword(encodedPassword);

        memberRepository.save(member);

        // 비밀번호 업데이트 시 모든 기기에서 로그아웃 한다.
        // 존재하는 모든 리프레쉬 토큰 확인 후 제거
        String memberId = String.valueOf(member.getId());
        // 존재하는 모든 uuid - refreshToken 가져오기
        Map<String, String> allDevice = refreshRepository.getAllFromHash(memberId);
        for (String uuid : allDevice.keySet()) {
            refreshRepository.delete(memberId + "-" + uuid);
            refreshRepository.delete(uuid);
            refreshRepository.removeFromHash(memberId, uuid);
        }


    }

    private void checkAuthorized(Long targetId, Long principalId) {
        if (!targetId.equals(principalId)) {
            throw new MemberUnauthorizedException(MemberErrorCode.UNAUTHORIZED_ACCESS_ERROR);
        }
    }

    private Member findExistMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));

    }

}
