package com.jamjam.bookjeok.domains.question.service;

import com.jamjam.bookjeok.domains.question.dto.QuestionDTO;
import com.jamjam.bookjeok.domains.question.dto.QuestionListDTO;
import com.jamjam.bookjeok.domains.question.dto.request.QuestionAnswerRequest;
import com.jamjam.bookjeok.domains.question.dto.response.QuestionAnswerResponse;
import com.jamjam.bookjeok.exception.question.InconsistentQuestionException;
import com.jamjam.bookjeok.exception.question.NotFoundQuestionException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class QuestionAdminServiceTest {

    @Autowired
    QuestionAdminService questionAdminService;

    @DisplayName("문의사항 조회 테스트")
    @Test
    void testFindQuestions() {

        Map<String, Object> params = new HashMap<>();

        List<QuestionListDTO> questions = questionAdminService.findQuestions(params);

        assertThat(questions).isNotNull();

        questions.forEach(System.out::println);

    }

    @DisplayName("문의사항 상세 조회 테스트")
    @Test
    void findQuestionByQuestionId() throws Exception {

        Map<String, Object> params = new HashMap<>();
        Long questionId = 2L;
        params.put("questionId", questionId);

        QuestionDTO question = questionAdminService.findQuestionByQuestionId(params);

        assertThat(question).isNotNull();
        log.info("{}", question);

    }

    @DisplayName("문의사항 답변 등록 테스트")
    @Test
    void testRegistQuestionAnswer() {

        Long questionId = 2L;
        Long writerUid = 99L; // 관리자 UID
        String answerContent = "환불이 완료되었습니다.";

        QuestionAnswerRequest request = QuestionAnswerRequest.builder()
                .questionId(questionId)
                .writerUid(writerUid)
                .contents(answerContent)
                .build();

        QuestionAnswerResponse response = questionAdminService.registQuestionAnswer(request);

        assertThat(response).isNotNull();
        assertThat(response.answerId()).isNotNull();
        assertThat(response.questionId()).isEqualTo(questionId);
        assertThat(response.writerUid()).isEqualTo(writerUid);
        assertThat(response.contents()).isEqualTo(answerContent);

    }

    @DisplayName("문의사항 답변 수정 테스트")
    @Test
    void testModifyQuestionAnswer() {

        Long answerId = 2L;
        Long questionId = 2L;
        Long writerUid = 99L; // 관리자 UID
        String answerContent = "환불이 완료되었습니다.";

        QuestionAnswerRequest request = QuestionAnswerRequest.builder()
                .questionId(questionId)
                .writerUid(writerUid)
                .contents(answerContent)
                .build();

        QuestionAnswerResponse response = questionAdminService.modifyQuestionAnswer(answerId, request);

        assertThat(response).isNotNull();
        assertThat(response.answerId()).isNotNull();
        assertThat(response.questionId()).isEqualTo(questionId);
        assertThat(response.writerUid()).isEqualTo(writerUid);
        assertThat(response.contents()).isEqualTo(answerContent);
        assertThat(response.modifiedAt()).isNotNull();

    }

    @DisplayName("문의사항 답변 수정 예외 발생 테스트")
    @Test
    void testModifyQuestionAnswerException() {

        Long questionId = 2L;
        Long answerId = 2L;
        Long writerUid = 99L;

        String answerContent = "환불처리 완료되었습니다.";

        QuestionAnswerRequest request = QuestionAnswerRequest.builder()
                .questionId(3L)
                .contents(answerContent)
                .writerUid(writerUid)
                .build();

        assertThatThrownBy(()-> questionAdminService.modifyQuestionAnswer(999L, request))
                .isInstanceOf(NotFoundQuestionException.class)
                .hasMessage("존재하지 않는 답변입니다.");


        assertThatThrownBy(() -> questionAdminService.modifyQuestionAnswer(answerId, request))
                .isInstanceOf(InconsistentQuestionException.class)
                .hasMessage("답변이 일치하지 않습니다.");

    }

    @DisplayName("문의사항 답변 삭제 테스트")
    @Test
    void testDeleteQuestionAnswer() {

        Long answerId = 2L;

        QuestionAnswerResponse response = questionAdminService.deleteQuestionAnswer(answerId);

        assertThat(response).isNotNull();

        assertThat(response.answerId()).isEqualTo(answerId);
        assertThat(response.modifiedAt()).isNotNull();
        assertThat(response.isDeleted()).isEqualTo(true);

    }

}
