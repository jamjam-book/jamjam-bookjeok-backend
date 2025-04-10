package com.jamjam.bookjeok.domains.member.repository.mapper;

import com.jamjam.bookjeok.domains.member.dto.response.InterestAuthorDTO;
import com.jamjam.bookjeok.domains.member.dto.response.InterestBookDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InterestAuthorMapper {

    List<InterestAuthorDTO> findInterestAuthorByMemberId(String memberId);

}
