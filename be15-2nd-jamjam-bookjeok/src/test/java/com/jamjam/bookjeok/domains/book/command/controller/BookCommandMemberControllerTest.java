package com.jamjam.bookjeok.domains.book.command.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.book.command.dto.request.ReviewRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookCommandMemberControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private static final String BASE_URL = "/api/v1";

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("리뷰 등록 테스트")
    @Test
    void testWriteReview() throws Exception{

        Long bookId = 1L;
        Long memberUid = 1L;

        ReviewRequest request = ReviewRequest.builder()
                .bookId(bookId)
                .memberUid(memberUid)
                .content("정말 재밌어요!!")
                .rating(4)
                .build();

        String content = mapper.writeValueAsString(request);
        String isbn = "9787245873163";

        mvc.perform(post(BASE_URL + "/book/{isbn}}/review", isbn)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.bookId").value(bookId))
                .andExpect(jsonPath("$.data.memberUid").value(memberUid))
                .andExpect(jsonPath("$.data.content").value("정말 재밌어요!!"))
                .andExpect(jsonPath("$.data.rating").value(4));
    }

    @DisplayName("리뷰 수정 테스트")
    @Test
    void testModifyReview() throws Exception{

        String isbn = "9781082502224";
        Long reviewId = 1L;
        Long bookId = 19L;
        Long memberUid = 2L;

        ReviewRequest request = ReviewRequest.builder()
                .bookId(bookId)
                .memberUid(memberUid)
                .content("문장이 너무 아름다웠어요!")
                .rating(5)
                .build();

        String content = mapper.writeValueAsString(request);

        mvc.perform(put(BASE_URL + "/book/{isbn}/review/{reviewId}", isbn, reviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberUid").value(memberUid))
                .andExpect(jsonPath("$.data.bookId").value(bookId))
                .andExpect(jsonPath("$.data.content").value("문장이 너무 아름다웠어요!"))
                .andExpect(jsonPath("$.data.rating").value(5));
    }

    @DisplayName("리뷰 삭제 테스트")
    @Test
    void testDeleteCategory() throws Exception{

        String isbn = "9791141601713";
        Long reviewId = 1L;

        mvc.perform(delete(BASE_URL + "/book/{isbn}/review/{reviewId}", isbn, reviewId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
