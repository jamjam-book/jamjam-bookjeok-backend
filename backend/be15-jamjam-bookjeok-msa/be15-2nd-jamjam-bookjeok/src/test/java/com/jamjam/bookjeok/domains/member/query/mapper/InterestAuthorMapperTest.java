package com.jamjam.bookjeok.domains.member.query.mapper;

import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.query.dto.InterestAuthorDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
class InterestAuthorMapperTest {

    @Autowired
    private InterestAuthorMapper interestAuthorMapper;

    @DisplayName("특정 회원이 즐겨찾기한 작가 목록 가져오기")
    @Test
    void findInterestBookListTest(){
        String memberId = "user02";
        PageRequest pageRequest = new PageRequest(1,10);

        List<InterestAuthorDTO> interestAuthorList =
                interestAuthorMapper.findInterestAuthorByMemberId(memberId, pageRequest);

        assertNotNull(interestAuthorList);
        interestAuthorList.forEach(System.out::println);

    }

    @DisplayName("특정 회원의 관심 작가 수 조회하기")
    @Test
    void countInterestAuthorsByMemberId(){
        String memberId = "user02";

        Long count = interestAuthorMapper.countInterestAuthorsByMemberId(memberId);

        assertEquals(2, count);
    }
}