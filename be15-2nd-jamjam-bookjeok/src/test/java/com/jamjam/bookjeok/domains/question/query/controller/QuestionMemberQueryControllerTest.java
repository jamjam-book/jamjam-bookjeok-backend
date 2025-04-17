package com.jamjam.bookjeok.domains.question.query.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class QuestionMemberQueryControllerTest {

    @Autowired
    private MockMvc mvc;

    private static final String BASE_URL = "/api/v1";

    @DisplayName("회원 문의사항 조회 테스트")
    @Test
    void testFindMemberQuestions() throws Exception {

        Long memberUid = 1L;

        mvc.perform(get(BASE_URL + "/{memberUid}/question", memberUid)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("questionId")));
    }

    @DisplayName("회원 문의사항 상세 조회 테스트")
    @Test
    void testFindMemberQuestionDetail() throws Exception {

        Long memberUid = 1L;
        Long questionId = 1L;

        mvc.perform(get(BASE_URL + "/{memberUid}/question/{questionId}", memberUid, questionId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("contents")));

    }

    @DisplayName("회원 문의사항 등록 페이지 호출 시 문의사항 카테고리 조회 테스트")
    @Test
    void testGetQuestionInsertPage() throws Exception {
        Long memberUid = 1L;

        mvc.perform(get(BASE_URL + "/{memberUid}/question/in", memberUid)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(("questionCategoryId"))));

    }

}
