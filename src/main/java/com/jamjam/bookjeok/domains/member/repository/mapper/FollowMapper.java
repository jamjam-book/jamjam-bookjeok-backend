package com.jamjam.bookjeok.domains.member.repository.mapper;

import com.jamjam.bookjeok.domains.member.dto.response.FollowDTO;
import com.jamjam.bookjeok.domains.member.dto.response.PostSummaryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowMapper{

    List<FollowDTO> findFollowingListByMemberId(String memberId);

}
