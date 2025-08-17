package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.common.dto.Pagination;
import com.jamjam.bookjeok.domains.member.query.dto.InterestBookDTO;
import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.InterestBookListResponse;
import com.jamjam.bookjeok.domains.member.query.mapper.InterestBookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterestBookQueryServiceImpl implements InterestBookQueryService{

    private final InterestBookMapper interestBookMapper;

    @Override
    @Transactional(readOnly = true)
    public InterestBookListResponse getInterestBookListByMemberId(
            String memberId,
            PageRequest pageRequest
    ){
        List<InterestBookDTO> interestBookList = interestBookMapper.findInterestBookList(memberId,pageRequest);

        long totalBooks = interestBookList.size();

        int page = pageRequest.getPage();
        int size = pageRequest.getSize();

        return InterestBookListResponse.builder()
                .interestBookList(interestBookList)
                .pagination(Pagination.builder()
                        .currentPage(page)
                        .totalPage((int)Math.ceil((double)totalBooks/size))
                        .totalItems(totalBooks)
                        .build())
                .build();
    }
}
