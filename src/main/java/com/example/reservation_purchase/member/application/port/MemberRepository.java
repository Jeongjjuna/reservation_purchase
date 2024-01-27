package com.example.reservation_purchase.member.application.port;

import com.example.reservation_purchase.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    Page<Member> findAllByIdIn(List<Long> ids, Pageable pageable);
}
