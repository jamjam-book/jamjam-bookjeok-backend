package com.jamjam.bookjeok.domains.member.service;

import com.jamjam.bookjeok.domains.member.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.dto.InterestBookDTO;
import com.jamjam.bookjeok.domains.member.dto.response.InterestBookListResponse;
import com.jamjam.bookjeok.domains.member.repository.mapper.InterestBookMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class InterestBookServiceTest {

    @Mock
    private InterestBookMapper interestBookMapper;

    @InjectMocks
    private InterestBookService interestBookService;

    @DisplayName("멤버의 책 즐겨 찾기 목록 가져오기")
    @Test
    void getInterestBookListByMemberIdTest(){
        String memberId = "user01";

        List<InterestBookDTO> mockBookList = List.of(
                new InterestBookDTO(
                        "책01",
                        "안녕안녕",
                        "이미지1",
                        "작가01"
                ),
                new InterestBookDTO(
                        "책02",
                        "안녕안녕안녕",
                        "이미지2",
                        "작가02"
                )
        );

        PageRequest pageRequest = new PageRequest(1,10);

        when(interestBookMapper.findInterestBookList(memberId, pageRequest)).thenReturn(mockBookList);

        InterestBookListResponse response =
                interestBookService.getInterestBookListByMemberId(memberId,pageRequest);

        assertNotNull(response);
        assertEquals(2, response.getInterestBookList().size());
        assertEquals(1, response.getPagination().getCurrentPage());
        mockBookList.forEach(System.out::println);
    }
}