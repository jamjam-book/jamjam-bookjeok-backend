package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.domains.member.query.dto.FollowDTO;
import com.jamjam.bookjeok.domains.member.query.dto.PostSummaryDTO;
import com.jamjam.bookjeok.domains.member.query.mapper.FollowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowQueryServiceImpl implements FollowQueryService{

    private final FollowMapper followMapper;

    @Override
    @Transactional(readOnly = true)
    public List<FollowDTO> getFollowingListByMemberId(String memberId) {
        return followMapper.findFollowingListByMemberId(memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostSummaryDTO> getPostListByWriterId(String writerId) {
        return followMapper.findPostListByMemberId(writerId);
    }

}