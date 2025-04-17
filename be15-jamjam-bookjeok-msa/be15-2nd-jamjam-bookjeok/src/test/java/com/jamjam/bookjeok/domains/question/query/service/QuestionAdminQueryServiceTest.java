package com.jamjam.bookjeok.domains.question.query.service;

import com.jamjam.bookjeok.domains.question.query.dto.QuestionDTO;
import com.jamjam.bookjeok.domains.question.query.dto.QuestionListDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class QuestionAdminQueryServiceTest {

    @Autowired
    QuestionAdminQueryService questionAdminQueryService;

    @DisplayName("문의사항 조회 테스트")
    @Test
    void testFindQuestions() {

        Map<String, Object> params = new HashMap<>();

        List<QuestionListDTO> questions = questionAdminQueryService.findQuestions(params);

        assertThat(questions).isNotNull();

        questions.forEach(System.out::println);

    }

    @DisplayName("문의사항 상세 조회 테스트")
    @Test
    void findQuestionByQuestionId() throws Exception {

        Map<String, Object> params = new HashMap<>();
        Long questionId = 2L;
        params.put("questionId", questionId);

        QuestionDTO question = questionAdminQueryService.findQuestionByQuestionId(params);

        assertThat(question).isNotNull();
        log.info("{}", question);

    }

}
