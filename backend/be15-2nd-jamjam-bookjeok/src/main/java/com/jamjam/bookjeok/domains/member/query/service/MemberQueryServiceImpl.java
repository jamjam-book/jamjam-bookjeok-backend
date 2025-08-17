package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.domains.member.command.dto.response.MemberDetailResponse;
import com.jamjam.bookjeok.domains.member.command.dto.response.MemberListResponse;
import com.jamjam.bookjeok.domains.member.query.dto.MemberDTO;
import com.jamjam.bookjeok.domains.member.query.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService{

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