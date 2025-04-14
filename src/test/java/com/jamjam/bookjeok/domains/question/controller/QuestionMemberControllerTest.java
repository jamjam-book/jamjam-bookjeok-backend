package com.jamjam.bookjeok.domains.question.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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
public class QuestionMemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    @DisplayName("회원 문의사항 등록 테스트")
    @Test
    void testRegistQuestion() throws Exception {
        Long memberUid = 1L;

        MockMultipartFile imageFile = new MockMultipartFile(
                "questionImg", "cover.png", "image/png", "fake image data".getBytes());

        MockMultipartFile jsonFile = new MockMultipartFile(
                "request", "request", "application/json",
                """
                {
                  "writerUid": 1,
                  "questionCategoryId": 1,
                  "title": "등록성공",
                  "contents": "등록 잘 되었는지 확인해주실 수 있나요?"
                }
                """.getBytes()
        );

        mvc.perform(multipart(BASE_URL + "/{memberUid}/question/in", memberUid)
                    .file(imageFile)
                    .file(jsonFile)
                    .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.questionId").exists())
                .andExpect(jsonPath("$.data.writerUid").value(1L))
                .andExpect(jsonPath("$.data.questionCategoryId").value(1L))
                .andExpect(jsonPath("$.data.title").value("등록성공"))
                .andExpect(jsonPath("$.data.contents").value("등록 잘 되었는지 확인해주실 수 있나요?"));

    }

    @DisplayName("회원 문의사항 등록 필수값 누락 예외 발생 테스트")
    @Test
    void testRegistQuestionException() throws Exception {
        Long memberUid = 1L;

        MockMultipartFile imageFile = new MockMultipartFile(
                "questionImg", "cover.png", "image/png", "fake image data".getBytes());

        MockMultipartFile jsonFile = new MockMultipartFile(
                "request", "request", "application/json",
                """
                {
                  "writerUid": 1,
                  "questionCategoryId": null,
                  "title": "등록성공",
                  "contents": "등록 잘 되었는지 확인해주실 수 있나요?"
                }
                """.getBytes()
        );

        mvc.perform(multipart(HttpMethod.POST, BASE_URL + "/{memberUid}/question/in", memberUid)
                        .file(imageFile)
                        .file(jsonFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("문의 항목은 반드시 선택해야 합니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

    }

    @DisplayName("문의사항 수정 테스트")
    @Test
    void testModifyQuestion() throws Exception {
        Long memberUid = 7L;
        Long questionId = 6L;

        MockMultipartFile imageFile = new MockMultipartFile(
                "questionImg", "cover.png", "image/png", "fake image data".getBytes());

        MockMultipartFile jsonFile = new MockMultipartFile(
                "request", "request", "application/json",
                """
                {
                  "writerUid": 7,
                  "questionCategoryId": 2,
                  "title": "수정 성공",
                  "contents": "수정 잘 되었는지 확인해주실 수 있나요?"
                }
                """.getBytes()
        );

        mvc.perform(multipart(HttpMethod.PUT,BASE_URL + "/{memberUid}/question/mod/{questionId}", memberUid, questionId)
                        .file(imageFile)
                        .file(jsonFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.questionId").exists())
                .andExpect(jsonPath("$.data.modifiedAt").exists())
                .andExpect(jsonPath("$.data.writerUid").value(memberUid))
                .andExpect(jsonPath("$.data.questionCategoryId").value(2L))
                .andExpect(jsonPath("$.data.title").value("수정 성공"))
                .andExpect(jsonPath("$.data.contents").value("수정 잘 되었는지 확인해주실 수 있나요?"));
    }

    @DisplayName("이미 답변 받은 문의 사항 수정 시 예외 발생 테스트")
    @Test
    void testModifyQuestionValidReceiveAnswer() throws Exception {

        Long memberUid = 1L;
        Long questionId = 1L;

        MockMultipartFile imageFile = new MockMultipartFile(
                "questionImg", "cover.png", "image/png", "fake image data".getBytes());

        MockMultipartFile jsonFile = new MockMultipartFile(
                "request", "request", "application/json",
                """
                {
                  "writerUid": 1,
                  "questionCategoryId": 1,
                  "title": "예외 발생",
                  "contents": "예외가 발생했나요?"
                }
                """.getBytes()
        );

        mvc.perform(multipart(HttpMethod.PUT,BASE_URL + "/{memberUid}/question/mod/{questionId}", memberUid, questionId)
                        .file(imageFile)
                        .file(jsonFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("이미 답변을 받은 문의사항입니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @DisplayName("문의사항 삭제 테스트")
    @Test
    void testDeleteQuestion() throws Exception {

        Long memberUid = 1L;
        Long questionId = 1L;

        mvc.perform(delete(BASE_URL + "/{memberUid}/question/del/{questionId}", memberUid, questionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.isDeleted").value(true));
    }

}
