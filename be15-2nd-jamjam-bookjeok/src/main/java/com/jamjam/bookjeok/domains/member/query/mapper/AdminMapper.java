package com.jamjam.bookjeok.domains.member.query.mapper;

import com.jamjam.bookjeok.domains.member.command.dto.request.MemberSearchRequest;
import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.query.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<MemberDTO> findAllMember(PageRequest pageRequest);

    long countMembers();

    MemberDTO findMemberByIdOrNickname(MemberSearchRequest memberSearchRequest);

}
