package com.example.reservation_purchase.member.application.port;

import com.example.reservation_purchase.member.domain.Member;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findByEmail(String email);
}
