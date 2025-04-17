package com.jamjam.bookjeok.domains.question.query.service;

import com.jamjam.bookjeok.domains.question.query.dto.QuestionDTO;
import com.jamjam.bookjeok.domains.question.query.dto.QuestionListDTO;

import java.util.List;
import java.util.Map;

public interface QuestionAdminQueryService {
    List<QuestionListDTO> findQuestions(Map<String, Object> params);

    QuestionDTO findQuestionByQuestionId(Map<String, Object> params);
}
