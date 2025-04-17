package com.jamjam.bookjeok.domains.member.query.service;


import com.jamjam.bookjeok.domains.member.query.dto.InterestAuthorDTO;
import com.jamjam.bookjeok.domains.member.query.mapper.InterestAuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestAuthorQueryServiceImpl implements InterestAuthorQueryService{

    private final InterestAuthorMapper interestAuthorMapper;

    @Override
    @Transactional(readOnly = true)
    public List<InterestAuthorDTO> getInterestAuthorList(
            String memberId
    ){
        return interestAuthorMapper.findInterestAuthorByMemberId(memberId);
    }

}
