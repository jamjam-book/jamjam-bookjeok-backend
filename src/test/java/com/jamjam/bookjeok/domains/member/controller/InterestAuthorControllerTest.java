package com.jamjam.bookjeok.domains.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.member.dto.response.BookNameDTO;
import com.jamjam.bookjeok.domains.member.dto.response.InterestAuthorDTO;
import com.jamjam.bookjeok.domains.member.service.FollowService;
import com.jamjam.bookjeok.domains.member.service.InterestAuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class InterestAuthorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private InterestAuthorService interestAuthorService;

    @DisplayName("멤버의 아이디로 즐겨찾기 한 작가 목록 가져오기")
    @Test
    void getInterestAuthorByMemberIdTest() throws Exception {
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

        when(interestAuthorService.getInterestAuthorList(memberId)).thenReturn(mockList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/{memberId}/interest-authors", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].authorName").value("작가A"))
                .andExpect(jsonPath("$.data[0].bookList[0].bookName").value("책1"))
                .andExpect(jsonPath("$.data[0].bookList[1].bookName").value("책2"))
                .andExpect(jsonPath("$.data[1].authorName").value("작가B"))
                .andExpect(jsonPath("$.data[1].bookList[0].bookName").value("책3"))
                .andExpect(jsonPath("$.data[1].bookList[1].bookName").value("책4"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());

    }

}
