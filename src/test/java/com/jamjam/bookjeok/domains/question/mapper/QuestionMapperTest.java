package com.jamjam.bookjeok.domains.question.mapper;

import com.jamjam.bookjeok.domains.question.dto.QuestionListDTO;
import com.jamjam.bookjeok.domains.question.repository.mapper.QuestionMapper;
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
public class QuestionMapperTest {

    @Autowired
    QuestionMapper questionMapper;

    @DisplayName("문의사항 조회 테스트")
    @Test
    void testFindQuestions() {

        Map<String, Object> params = new HashMap<>();
        params.put("isAnswered", false);

        List<QuestionListDTO> questions = questionMapper.findQuestions(params);

        assertThat(questions).isNotNull();

        questions.forEach(System.out::println);
    }
}





