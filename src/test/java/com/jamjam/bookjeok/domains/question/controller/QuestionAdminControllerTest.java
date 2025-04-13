package com.jamjam.bookjeok.domains.question.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.question.dto.request.QuestionAnswerRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
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
public class QuestionAdminControllerTest {

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

    @DisplayName("문의사항 답변 등록 테스트")
    @Test
    void testRegistQuestionAnswer() throws Exception {

        Long questionId = 2L;
        Long writerUid = 99L; // 관리자 UID
        String answerContent = "환불이 완료되었습니다.";

        QuestionAnswerRequest request = QuestionAnswerRequest.builder()
                .questionId(questionId)
                .contents(answerContent)
                .writerUid(writerUid)
                .build();

        String content = objectMapper.writeValueAsString(request);

        mvc.perform(post(BASE_URL + "/question/{questionId}", questionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.answerId").exists())
                .andExpect(jsonPath("$.data.questionId").value(questionId))
                .andExpect(jsonPath("$.data.writerUid").value(writerUid))
                .andExpect(jsonPath("$.data.contents").value(answerContent));

    }

    @DisplayName("문의사항 답변 등록 필수 항목 누락 시 예외 발생 테스트")
    @Test
    void testRegistQuestionAnswerException() throws Exception {

        Long questionId = 2L;
        Long writerUid = 99L; // 관리자 UID
        String answerContent = null;

        QuestionAnswerRequest request = QuestionAnswerRequest.builder()
                .questionId(questionId)
                .contents(answerContent)
                .writerUid(writerUid)
                .build();

        String content = objectMapper.writeValueAsString(request);

        mvc.perform(post(BASE_URL + "/question/{questionId}", questionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("답변이 입력되지 않았습니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

    }

    @DisplayName("문의사항 답변 수정 테스트")
    @Test
    void testModifyQuestionAnswer() throws Exception {

        Long questionId = 2L;
        Long answerId = 2L;
        Long writerUid = 99L;

        String answerContent = "환불처리 완료되었습니다.";

        QuestionAnswerRequest request = QuestionAnswerRequest.builder()
                .questionId(questionId)
                .contents(answerContent)
                .writerUid(writerUid)
                .build();

        String content = objectMapper.writeValueAsString(request);

        mvc.perform(put(BASE_URL + "/question/{questionId}/{answerId}", questionId, answerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.answerId").exists())
                .andExpect(jsonPath("$.data.questionId").value(questionId))
                .andExpect(jsonPath("$.data.writerUid").value(writerUid))
                .andExpect(jsonPath("$.data.contents").value(answerContent));

    }

    @DisplayName("문의사항 답변 수정 예외 발생 테스트")
    @Test
    void testModifyQuestionAnswerException() throws Exception {

        Long questionId = 2L;
        Long answerId = 2L;
        Long writerUid = 99L;

        String answerContent = "환불처리 완료되었습니다.";


        QuestionAnswerRequest request = QuestionAnswerRequest.builder()
                .questionId(3L)
                .contents(answerContent)
                .writerUid(writerUid)
                .build();

        String content = objectMapper.writeValueAsString(request);

        // 존재하지 않는 답변 수정 시
        mvc.perform(put(BASE_URL + "/question/{questionId}/{answerId}", questionId, 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("존재하지 않는 답변입니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        // 수정해야 하는 답변과 수정 요청한 답변이 다를 경우
        mvc.perform(put(BASE_URL + "/question/{questionId}/{answerId}", questionId, answerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("답변이 일치하지 않습니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @DisplayName("문의사항 답변 삭제 테스트")
    @Test
    void testDeleteQuestionAnswer() throws Exception {

        Long questionId = 2L;
        Long answerId = 2L;

        mvc.perform(delete(BASE_URL + "/question/{questionId}/{answerId}", questionId, answerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.answerId").exists())
                .andExpect(jsonPath("$.data.questionId").value(questionId))
                .andExpect(jsonPath("$.data.modifiedAt").exists())
                .andExpect(jsonPath("$.data.isDeleted").value(true));
    }
}