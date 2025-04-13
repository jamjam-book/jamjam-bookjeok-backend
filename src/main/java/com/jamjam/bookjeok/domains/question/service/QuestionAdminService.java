package com.jamjam.bookjeok.domains.question.service;

import com.jamjam.bookjeok.domains.question.dto.QuestionListDTO;
import com.jamjam.bookjeok.domains.question.dto.request.QuestionAnswerRequest;
import com.jamjam.bookjeok.domains.question.dto.response.QuestionAnswerResponse;

import java.util.List;
import java.util.Map;

public interface QuestionAdminService {
    List<QuestionListDTO> findQuestions(Map<String, Object> params);

    QuestionAnswerResponse registQuestionAnswer(QuestionAnswerRequest request);

    QuestionAnswerResponse modifyQuestionAnswer(Long answerId, QuestionAnswerRequest request);

    QuestionAnswerResponse deleteQuestionAnswer(Long answerId);
}
