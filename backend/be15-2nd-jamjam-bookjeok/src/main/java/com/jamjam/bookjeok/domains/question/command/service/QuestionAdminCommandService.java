package com.jamjam.bookjeok.domains.question.command.service;

import com.jamjam.bookjeok.domains.question.command.dto.request.QuestionAnswerRequest;
import com.jamjam.bookjeok.domains.question.command.dto.response.QuestionAnswerResponse;

public interface QuestionAdminCommandService {

    QuestionAnswerResponse registQuestionAnswer(QuestionAnswerRequest request);

    QuestionAnswerResponse modifyQuestionAnswer(Long answerId, QuestionAnswerRequest request);

    QuestionAnswerResponse deleteQuestionAnswer(Long answerId);

}
