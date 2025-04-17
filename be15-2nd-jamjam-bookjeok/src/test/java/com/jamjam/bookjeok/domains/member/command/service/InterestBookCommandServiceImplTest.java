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
        Long memberUid = 1L;
        InterestBookRequest request = new InterestBookRequest(1L);

        String bookName
                = interestBookCommandService.createInterestBook(memberUid, request);

        assertEquals("우리가 빛의 속도로 갈 수 없다면", bookName);
    }

    @DisplayName("없는 책이라면 발생하는 예외")
    @Test
    void notFoundBookExceptionTest(){
        Long memberUid = 1L;
        InterestBookRequest request = new InterestBookRequest(1000L);

        assertThrows(NotFoundBookException.class,
                () ->  interestBookCommandService.createInterestBook(memberUid, request));
    }

    @DisplayName("이미 즐겨찾기에 등록된 도서일 경우 발생하는 예외")
    @Test
    void alreadyInterestedBookExceptionTest(){
        Long memberUid = 1L;
        InterestBookRequest request = new InterestBookRequest(2L);

        assertThrows(AlreadyInterestedBookException.class,
                () ->  interestBookCommandService.createInterestBook(memberUid, request));
    }

    @DisplayName("관심 도서 삭제 후 존재 여부 확인해 예외 발생")
    @Test
    void deleteInterestAuthorTest(){
        Long memberUid = 2L;

        InterestBookRequest request = new InterestBookRequest(2L);

        interestBookCommandService.deleteInterestBook(memberUid, request);

        assertThrows(NotFoundBookException.class, () -> {
            interestBookCommandService.deleteInterestBook(memberUid, request);
        });
    }
}
