package com.jamjam.bookjeok.domains.question.command.service;

import com.jamjam.bookjeok.domains.question.command.dto.request.QuestionAnswerRequest;
import com.jamjam.bookjeok.domains.question.command.dto.response.QuestionAnswerResponse;
import com.jamjam.bookjeok.exception.question.InconsistentQuestionException;
import com.jamjam.bookjeok.exception.question.NotFoundQuestionException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class QuestionAdminCommandServiceTest {

    @Autowired
    QuestionAdminCommandService questionAdminCommandService;

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

        QuestionAnswerResponse response = questionAdminCommandService.registQuestionAnswer(request);

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

        QuestionAnswerResponse response = questionAdminCommandService.modifyQuestionAnswer(answerId, request);

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

        assertThatThrownBy(()-> questionAdminCommandService.modifyQuestionAnswer(999L, request))
                .isInstanceOf(NotFoundQuestionException.class)
                .hasMessage("존재하지 않는 답변입니다.");


        assertThatThrownBy(() -> questionAdminCommandService.modifyQuestionAnswer(answerId, request))
                .isInstanceOf(InconsistentQuestionException.class)
                .hasMessage("답변이 일치하지 않습니다.");

    }

    @DisplayName("문의사항 답변 삭제 테스트")
    @Test
    void testDeleteQuestionAnswer() {

        Long answerId = 2L;

        QuestionAnswerResponse response = questionAdminCommandService.deleteQuestionAnswer(answerId);

        assertThat(response).isNotNull();

        assertThat(response.answerId()).isEqualTo(answerId);
        assertThat(response.modifiedAt()).isNotNull();
        assertThat(response.isDeleted()).isEqualTo(true);

    }

}
