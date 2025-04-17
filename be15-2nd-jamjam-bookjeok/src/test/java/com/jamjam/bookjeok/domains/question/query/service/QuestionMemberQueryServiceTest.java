package com.jamjam.bookjeok.domains.question.query.service;

import com.jamjam.bookjeok.domains.question.query.dto.QuestionCategoryDTO;
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
public class QuestionMemberQueryServiceTest {

    @Autowired
    QuestionMemberQueryService questionMemberQueryService;

    @DisplayName("회원 문의사항 조회 테스트")
    @Test
    void testFindMemberQuestions() {

        Long memberUid = 1L;
        Map<String, Object> params = new HashMap<>();
        params.put("memberUid", memberUid);

        List<QuestionListDTO> questions = questionMemberQueryService.findMemberQuestions(params);

        assertThat(questions).isNotNull();

        questions.forEach(System.out::println);

    }

    @DisplayName("회원 문의사항 상세 조회 테스트")
    @Test
    void findQuestionByQuestionId() throws Exception {

        Long memberUid = 1L;
        Long questionId = 1L;

        Map<String, Object> params = new HashMap<>();
        params.put("memberUid", memberUid);
        params.put("questionId", questionId);

        QuestionDTO question = questionMemberQueryService.findMemberQuestion(params);

        assertThat(question).isNotNull();
        log.info("{}", question);

    }

    @DisplayName("문의사항 카테고리 조회 테스트")
    @Test
    void testGetQuestionCategories() {

        List<QuestionCategoryDTO> categories = questionMemberQueryService.getQuestionCategories();

        assertThat(categories).isNotNull();

        categories.forEach(System.out::println);

    }
}
