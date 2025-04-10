package com.jamjam.bookjeok.domains.member.repository.mapper;

import com.jamjam.bookjeok.domains.member.dto.InterestAuthorDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InterestAuthorMapper {

    List<InterestAuthorDTO> findInterestAuthorByMemberId(String memberId);

}
