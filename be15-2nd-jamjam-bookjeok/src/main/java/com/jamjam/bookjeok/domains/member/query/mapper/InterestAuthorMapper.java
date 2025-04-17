package com.jamjam.bookjeok.domains.member.query.mapper;

import com.jamjam.bookjeok.domains.member.query.dto.InterestAuthorDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InterestAuthorMapper {

    List<InterestAuthorDTO> findInterestAuthorByMemberId(String memberId);

    int countInterestAuthor(Long memberUid);

}
