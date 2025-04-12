package com.jamjam.bookjeok.domains.member.repository.mapper;

import com.jamjam.bookjeok.domains.member.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.dto.InterestBookDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InterestBookMapper {

    List<InterestBookDTO> findInterestBookList(
            @Param("memberId") String memberId,
            @Param("pageRequest") PageRequest pageRequest
    );
}
