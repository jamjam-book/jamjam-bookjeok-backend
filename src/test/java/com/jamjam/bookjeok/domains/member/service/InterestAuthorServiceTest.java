package com.jamjam.bookjeok.domains.member.service;

import com.jamjam.bookjeok.domains.member.dto.response.BookNameDTO;
import com.jamjam.bookjeok.domains.member.dto.response.InterestAuthorDTO;
import com.jamjam.bookjeok.domains.member.dto.response.InterestBookListResponse;
import com.jamjam.bookjeok.domains.member.repository.mapper.InterestAuthorMapper;
import com.jamjam.bookjeok.domains.member.repository.mapper.InterestBookMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class InterestAuthorServiceTest {

    @Mock
    private InterestAuthorMapper interestAuthorMapper;

    @InjectMocks
    private InterestAuthorService interestAuthorService;

    @DisplayName("회원의 책 즐겨찾기 목록 가져오기")
    @Test
    void findInterestAuthorByMemberIdTest(){
        String memberId = "user01";

        InterestAuthorDTO interestAuthor1 = new InterestAuthorDTO();
        interestAuthor1.setAuthorName("작가A");

        BookNameDTO book1 = new BookNameDTO();
        book1.setBookName("책1");

        BookNameDTO book2 = new BookNameDTO();
        book2.setBookName("책2");

        interestAuthor1.setBookList(List.of(book1, book2));

        InterestAuthorDTO interestAuthor2 = new InterestAuthorDTO();
        interestAuthor2.setAuthorName("작가B");

        BookNameDTO book3 = new BookNameDTO();
        book3.setBookName("책3");

        BookNameDTO book4 = new BookNameDTO();
        book4.setBookName("책4");

        interestAuthor2.setBookList(List.of(book3, book4));


        List<InterestAuthorDTO> mockList = List.of(interestAuthor1,interestAuthor2);

        when(interestAuthorMapper.findInterestAuthorByMemberId(memberId)).thenReturn(mockList);

        List<InterestAuthorDTO> interestAuthorList =
                interestAuthorService.getInterestAuthorList(memberId);

        assertNotNull(interestAuthorList);
        assertEquals("작가A", interestAuthor1.getAuthorName());
        assertEquals("작가B", interestAuthor2.getAuthorName());

        interestAuthorList.forEach(interestAuthorDTO -> System.out.println(interestAuthorDTO));

    }
}
