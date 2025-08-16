package com.jamjam.bookjeok.domains.question.query.service;

import com.jamjam.bookjeok.domains.question.query.dto.QuestionCategoryDTO;
import com.jamjam.bookjeok.domains.question.query.dto.QuestionDTO;
import com.jamjam.bookjeok.domains.question.query.dto.QuestionListDTO;

import java.util.List;
import java.util.Map;

public interface QuestionMemberQueryService {
    List<QuestionListDTO> findMemberQuestions(Map<String, Object> params);

    QuestionDTO findMemberQuestion(Map<String, Object> params);

    List<QuestionCategoryDTO> getQuestionCategories();
}
