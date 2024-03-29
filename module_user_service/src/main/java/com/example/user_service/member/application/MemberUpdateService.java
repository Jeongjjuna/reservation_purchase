package com.example.user_service.member.application;

import com.example.user_service.auth.application.port.RedisRefreshRepository;
import com.example.user_service.member.application.port.MemberRepository;
import com.example.user_service.member.domain.Member;
import com.example.user_service.member.domain.MemberUpdate;
import com.example.user_service.member.domain.PasswordUpdate;
import com.example.user_service.member.exception.MemberErrorCode;
import com.example.user_service.member.exception.MemberException.MemberNotFoundException;
import com.example.user_service.member.exception.MemberException.MemberUnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@AllArgsConstructor
@Service
public class MemberUpdateService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisRefreshRepository redisRefreshRepository;

    /**
     * 이름, 인사말 업데이트
     */
    @Transactional
    public void update(final MemberUpdate memberUpdate, final Long targetId, final Long principalId) {

        checkAuthorized(targetId, principalId);

        final Member member = findExistMember(targetId);

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

        final Member member = findExistMember(targetId);

        passwordUpdate.validate();

        final String encodedPassword = passwordEncoder.encode(passwordUpdate.getPassword());
        member.applyEncodedPassword(encodedPassword);

        memberRepository.save(member);

        // 비밀번호 업데이트 시 모든 기기에서 로그아웃 한다.
        // 존재하는 모든 리프레쉬 토큰 확인 후 제거
        final String memberId = String.valueOf(member.getId());
        // 존재하는 모든 uuid - refreshToken 가져오기
        final Map<String, String> allDevice = redisRefreshRepository.getAllFromHash(memberId);
        for (final String uuid : allDevice.keySet()) {
            redisRefreshRepository.delete(memberId + "-" + uuid);
            redisRefreshRepository.delete(uuid);
            redisRefreshRepository.removeFromHash(memberId, uuid);
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

}
