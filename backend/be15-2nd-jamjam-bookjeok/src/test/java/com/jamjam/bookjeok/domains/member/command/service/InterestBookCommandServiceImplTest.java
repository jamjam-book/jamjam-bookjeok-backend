package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.InterestBookRequest;
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
class InterestBookCommandServiceImplTest {

    @Autowired
    private InterestBookCommandService interestBookCommandService;

    @DisplayName("관심 도서 추가하기")
    @Test
    void createInterestedBook(){
        String memberId = "user01";
        Long bookId = 5L;
        InterestBookRequest request = new InterestBookRequest(bookId, memberId);

        String bookName
                = interestBookCommandService.createInterestBook(request);

        assertEquals("노르웨이의 숲", bookName);
    }

    @DisplayName("없는 책이라면 발생하는 예외")
    @Test
    void notFoundBookExceptionTest(){
        String memberId = "user01";
        Long bookId = 1000L;

        InterestBookRequest request = new InterestBookRequest(bookId, memberId);

        assertThrows(NotFoundBookException.class,
                () ->  interestBookCommandService.createInterestBook(request));
    }

    @DisplayName("이미 즐겨찾기에 등록된 도서일 경우 발생하는 예외")
    @Test
    void alreadyInterestedBookExceptionTest(){
        String memberId = "user01";
        Long bookId = 2L;

        InterestBookRequest request = new InterestBookRequest(bookId, memberId);

        assertThrows(AlreadyInterestedBookException.class,
                () ->  interestBookCommandService.createInterestBook(request));
    }

    @DisplayName("관심 도서 삭제 후 존재 여부 확인해 예외 발생")
    @Test
    void deleteInterestAuthorTest(){
        String memberId = "user02";
        Long bookId = 2L;

        interestBookCommandService.deleteInterestBook(memberId, bookId);

        assertThrows(NotFoundBookException.class, () -> {
            interestBookCommandService.deleteInterestBook(memberId, bookId);
        });
    }
}
