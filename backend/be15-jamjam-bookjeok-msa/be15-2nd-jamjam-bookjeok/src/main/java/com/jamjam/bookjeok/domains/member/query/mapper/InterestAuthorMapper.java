package com.jamjam.bookjeok.domains.member.query.mapper;

import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.query.dto.InterestAuthorDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InterestAuthorMapper {

    List<InterestAuthorDTO> findInterestAuthorByMemberId(
            @Param("memberId") String memberId,
            @Param("pageRequest") PageRequest pageRequest
    );

    Long countInterestAuthorsByMemberId(String memberId);

}
