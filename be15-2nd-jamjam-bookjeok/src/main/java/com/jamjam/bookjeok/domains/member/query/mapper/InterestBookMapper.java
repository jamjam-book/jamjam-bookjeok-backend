package com.jamjam.bookjeok.domains.member.query.mapper;

import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.query.dto.InterestBookDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InterestBookMapper {

    List<InterestBookDTO> findInterestBookList(
            @Param("memberId") String memberId,
            @Param("pageRequest") PageRequest pageRequest
    );

    int countInterestBookByMemberUid(Long memberUid);

}
