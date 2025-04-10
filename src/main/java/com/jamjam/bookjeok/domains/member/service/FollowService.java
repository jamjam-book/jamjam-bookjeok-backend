package com.jamjam.bookjeok.domains.member.service;

import com.jamjam.bookjeok.domains.member.dto.response.FollowDTO;
import com.jamjam.bookjeok.domains.member.dto.response.PostSummaryDTO;
import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import com.jamjam.bookjeok.exception.member.MemberException;
import com.jamjam.bookjeok.domains.member.repository.mapper.FollowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowMapper followMapper;

    @Transactional(readOnly = true)
    public List<FollowDTO> getFollowingListByMemberId(String memberId) {

        return followMapper.findFollowingListByMemberId(memberId);
    }
}