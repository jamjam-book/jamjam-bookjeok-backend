package com.jamjam.bookjeok.domains.question.query.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class QuestionAdminQueryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/v1/admin";

    @DisplayName("문의사항 조회 테스트")
    @Test
    void testFindQuestions() throws Exception {

        Map<String, Object> params = new HashMap<>();

        mvc.perform(get(BASE_URL + "/question")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("params", objectMapper.writeValueAsString(params)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("questionId")));

    }

    @DisplayName("문의사항 상세 조회 테스트")
    @Test
    void findQuestionByQuestionId() throws Exception {

        Long questionId = 2L;

        mvc.perform(get(BASE_URL + "/question/{questionId}", questionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("contents")));

    }

}