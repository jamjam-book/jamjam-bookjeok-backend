package com.jamjam.bookjeok.domains.member.repository.mapper;

import com.jamjam.bookjeok.domains.member.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.dto.InterestBookDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class InterestBookMapperTest {

    @Autowired
    private InterestBookMapper interestBookMapper;

    @DisplayName("특정 회원의 책 즐겨찾기 목록 가져오기")
    @Test
    void findInterestBookListTest(){
        String memberId = "user01";
        PageRequest pageRequest = new PageRequest(1,10);

        List<InterestBookDTO> bookList = interestBookMapper.findInterestBookList(memberId, pageRequest);

        assertNotNull(bookList);
    }

}