package com.jamjam.bookjeok.domains.member.service;

import com.jamjam.bookjeok.domains.member.dto.request.InterestBookRequest;
import com.jamjam.bookjeok.domains.member.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.dto.response.InterestBookListResponse;
import com.jamjam.bookjeok.exception.member.interestBookException.AlreadyInterestedBookException;
import com.jamjam.bookjeok.exception.member.interestBookException.NotFoundBookException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class InterestBookServiceTest {

    @Autowired
    private InterestBookService interestBookService;

    @DisplayName("멤버의 책 즐겨 찾기 목록 가져오기")
    @Test
    void getInterestBookListByMemberIdTest(){
        String memberId = "user01";

        PageRequest pageRequest = new PageRequest(1,10);

        InterestBookListResponse response =
                interestBookService.getInterestBookListByMemberId(memberId,pageRequest);

        assertNotNull(response);
        assertEquals(1, response.getInterestBookList().size());
        assertEquals(1, response.getPagination().getCurrentPage());
    }

    @DisplayName("관심 도서 추가하기")
    @Test
    void createInterestedBook(){
        Long memberUid = 1L;
        InterestBookRequest request = new InterestBookRequest(1L);

        String bookName
                = interestBookService.createInterestBook(memberUid, request);

        assertEquals("우리가 빛의 속도로 갈 수 없다면", bookName);
    }

    @DisplayName("없는 책이라면 발생하는 예외")
    @Test
    void notFoundBookExceptionTest(){
        Long memberUid = 1L;
        InterestBookRequest request = new InterestBookRequest(1000L);

        assertThrows(NotFoundBookException.class,
                () ->  interestBookService.createInterestBook(memberUid, request));
    }

    @DisplayName("이미 즐겨찾기에 등록된 도서일 경우 발생하는 예외")
    @Test
    void alreadyInterestedBookExceptionTest(){
        Long memberUid = 1L;
        InterestBookRequest request = new InterestBookRequest(2L);

        assertThrows(AlreadyInterestedBookException.class,
                () ->  interestBookService.createInterestBook(memberUid, request));
    }

    @DisplayName("관심 도서 삭제 후 존재 여부 확인해 예외 발생")
    @Test
    void deleteInterestAuthorTest(){
        Long memberUid = 2L;

        InterestBookRequest request = new InterestBookRequest(2L);

        interestBookService.deleteInterestBook(memberUid, request);

        assertThrows(NotFoundBookException.class, () -> {
            interestBookService.deleteInterestBook(memberUid, request);
        });
    }
}
