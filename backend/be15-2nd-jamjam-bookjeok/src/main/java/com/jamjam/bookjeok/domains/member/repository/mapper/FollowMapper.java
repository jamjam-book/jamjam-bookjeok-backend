package com.jamjam.bookjeok.domains.member.repository.mapper;

import com.jamjam.bookjeok.domains.member.dto.FollowDTO;
import com.jamjam.bookjeok.domains.member.dto.PostSummaryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowMapper{

    List<FollowDTO> findFollowingListByMemberId(String memberId);

    List<PostSummaryDTO> findPostListByMemberId(String memberId);

}
