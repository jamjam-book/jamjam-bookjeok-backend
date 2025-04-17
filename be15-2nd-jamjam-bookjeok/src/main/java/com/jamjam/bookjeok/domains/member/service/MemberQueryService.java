package com.jamjam.bookjeok.domains.member.service;

import com.jamjam.bookjeok.domains.member.dto.MemberDTO;
import com.jamjam.bookjeok.domains.member.dto.response.MemberDetailResponse;
import com.jamjam.bookjeok.domains.member.dto.response.MemberListResponse;
import com.jamjam.bookjeok.domains.member.repository.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberMapper memberMapper;

    public MemberDetailResponse getMemberDetail(String memberId) {
        MemberDTO member = Optional.ofNullable(
                memberMapper.findMemberByMemberId(memberId)
        ).orElseThrow(() -> new RuntimeException("유저 정보 찾지 못함"));

        return MemberDetailResponse.builder().member(member).build();
    }

    public MemberListResponse getAllMembers() {
        List<MemberDTO> members = memberMapper.findAllMembers();
        return MemberListResponse.builder()
                .memberList(members)
                .build();
    }
}