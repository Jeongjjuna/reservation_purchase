package com.example.reservation_purchase.member.application.port;

import com.example.reservation_purchase.member.domain.Member;

public interface MemberRepository {
    Member save(Member member);
}
