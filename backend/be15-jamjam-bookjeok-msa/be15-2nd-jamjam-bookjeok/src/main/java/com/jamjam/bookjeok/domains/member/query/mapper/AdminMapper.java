package com.jamjam.bookjeok.domains.member.query.mapper;

import com.jamjam.bookjeok.domains.member.command.dto.request.MemberSearchRequest;
import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.query.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminMapper {

    List<MemberDTO> findAllMember(MemberSearchRequest memberSearchRequest);

    long countMembers(MemberSearchRequest memberSearchRequest);

    MemberDTO findMemberByMemberId(String memberId);
}
