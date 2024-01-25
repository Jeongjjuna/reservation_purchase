package com.example.reservation_purchase.auth.application;

import com.example.reservation_purchase.auth.domain.UserDetailsImpl;
import com.example.reservation_purchase.member.application.port.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    public UserDetailServiceImpl(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String userEmail) throws UsernameNotFoundException {
        return memberRepository.findByEmail(userEmail)
                .map(UserDetailsImpl::from)
                .orElseThrow(() -> new UsernameNotFoundException("[ERROR] User Not Found"));
    }
}
