package com.jamjam.bookjeok.domains.member.service;

import com.jamjam.bookjeok.domains.member.dto.InterestAuthorDTO;
import com.jamjam.bookjeok.domains.member.dto.request.InterestAuthorCreatRequest;
import com.jamjam.bookjeok.exception.member.interestAuthorException.AlreadyInterestedAuthorException;
import com.jamjam.bookjeok.exception.member.interestAuthorException.AuthorNotFoundException;
import com.jamjam.bookjeok.exception.member.interestAuthorException.InterestAuthorLimitExceededException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class InterestAuthorServiceTest {

    @Autowired
    private InterestAuthorService interestAuthorService;

    @DisplayName("회원의 책 즐겨찾기 목록 가져오기")
    @Test
    void findInterestAuthorByMemberIdTest(){
        String memberId = "user02";

        List<InterestAuthorDTO> interestAuthorList
                = interestAuthorService.getInterestAuthorList(memberId);

        assertNotNull(interestAuthorList);
    }

    @DisplayName("관심 작가 등록하기")
    @Test
    void createInterestAuthorTest(){
        Long memberUid = 2L;

        String authorName1 = "공지영";
        InterestAuthorCreatRequest request1 = new InterestAuthorCreatRequest(authorName1, memberUid);
        String response = interestAuthorService.createInterestAuthor(request1);
        assertEquals("공지영", response);

    }

    @DisplayName("관심 작가 등록시 작가가 없는 경우 예외 테스트")
    @Test
    void createInterestAuthorExceptionTest1(){
        Long memberUid = 2L;

        String authorName2 = "정유진";
        InterestAuthorCreatRequest request2 = new InterestAuthorCreatRequest(authorName2, memberUid);
        assertThrows(AuthorNotFoundException.class, () -> {
            interestAuthorService.createInterestAuthor(request2);
        });
    }

    @DisplayName("관심 작가 등록시 이미 등록 된 작가인 경우 예외 테스트")
    @Test
    void createInterestAuthorExceptionTest2(){
        Long memberUid = 2L;

        String authorName3 = "정약용";
        InterestAuthorCreatRequest request3 = new InterestAuthorCreatRequest(authorName3, memberUid);
        assertThrows(AlreadyInterestedAuthorException.class, () -> {
            interestAuthorService.createInterestAuthor(request3);
        });
    }

    @DisplayName("관심자가 30명 초과시 발생하는 예외 테스트")
    @Test
    void interestAuthorLimitTest(){
        Long memberUid = 2L;

        String authorName3 = "정약용";
        InterestAuthorCreatRequest request3 = new InterestAuthorCreatRequest(authorName3, memberUid);
        assertThrows(InterestAuthorLimitExceededException.class, () -> {
            interestAuthorService.createInterestAuthor(request3);
        });
    }

}
