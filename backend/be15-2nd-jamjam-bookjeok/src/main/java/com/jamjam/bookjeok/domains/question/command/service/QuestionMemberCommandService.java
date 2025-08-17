package com.jamjam.bookjeok.domains.question.command.service;

import com.jamjam.bookjeok.domains.question.command.dto.request.QuestionRequest;
import com.jamjam.bookjeok.domains.question.command.dto.response.QuestionResponse;
import org.springframework.web.multipart.MultipartFile;

public interface QuestionMemberCommandService {

    QuestionResponse registQuestion(QuestionRequest request, MultipartFile questionImg);

    void validReceivedAnswer(Long questionId);

    QuestionResponse modifyQuestion(Long questionId, QuestionRequest request, MultipartFile questionImg);

    QuestionResponse deleteQuestion(Long questionId);
}
