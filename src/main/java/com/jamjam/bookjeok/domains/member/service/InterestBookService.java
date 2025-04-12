package com.jamjam.bookjeok.domains.member.service;

import com.jamjam.bookjeok.common.dto.Pagination;
import com.jamjam.bookjeok.domains.member.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.dto.InterestBookDTO;
import com.jamjam.bookjeok.domains.member.dto.response.InterestBookListResponse;
import com.jamjam.bookjeok.domains.member.repository.mapper.InterestBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestBookService {

    private final InterestBookMapper interestBookMapper;

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
