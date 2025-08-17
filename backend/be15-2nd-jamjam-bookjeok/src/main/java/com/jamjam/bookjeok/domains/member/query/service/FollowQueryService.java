package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.domains.member.query.dto.FollowDTO;
import com.jamjam.bookjeok.domains.member.query.dto.PostSummaryDTO;

import java.util.List;

public interface FollowQueryService {
    List<FollowDTO> getFollowingListByMemberId(String memberId);

    List<PostSummaryDTO> getPostListByWriterId(String writerId);
}
