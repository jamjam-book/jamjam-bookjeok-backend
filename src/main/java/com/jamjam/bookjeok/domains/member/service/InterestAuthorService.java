package com.jamjam.bookjeok.domains.member.service;

import com.jamjam.bookjeok.domains.member.dto.response.InterestAuthorDTO;
import com.jamjam.bookjeok.domains.member.repository.mapper.InterestAuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestAuthorService {

    private final InterestAuthorMapper interestAuthorMapper;

    @Transactional(readOnly = true)
    public List<InterestAuthorDTO> getInterestAuthorList(
            String memberId
    ){
        return interestAuthorMapper.findInterestAuthorByMemberId(memberId);
    }
}
