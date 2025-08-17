package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.common.dto.Pagination;
import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.InterestAuthorListResponse;
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
    public InterestAuthorListResponse getInterestAuthorList(
            String memberId,
            PageRequest pageRequest
    ){
        List<InterestAuthorDTO> interestAuthorList
                = interestAuthorMapper.findInterestAuthorByMemberId(memberId,pageRequest);

        long totalAuthors = interestAuthorMapper.countInterestAuthorsByMemberId(memberId);

        int page = pageRequest.getPage();
        int size = pageRequest.getSize();

        return InterestAuthorListResponse.builder()
                .interestAuthorList(interestAuthorList)
                .pagination(Pagination.builder()
                        .currentPage(page)
                        .totalPage((int)Math.ceil((double)totalAuthors/size))
                        .totalItems(totalAuthors)
                        .build())
                .build();
    }
}
