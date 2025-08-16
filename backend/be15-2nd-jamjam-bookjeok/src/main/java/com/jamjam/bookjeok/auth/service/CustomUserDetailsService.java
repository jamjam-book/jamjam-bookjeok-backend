package com.jamjam.bookjeok.auth.service;

import com.jamjam.bookjeok.domains.member.entity.Member;
import com.jamjam.bookjeok.domains.member.repository.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("유저 찾지 못함"));

        return new org.springframework.security.core.userdetails.User(
                member.getMemberId(),
                member.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().name()))
        );
    }
}