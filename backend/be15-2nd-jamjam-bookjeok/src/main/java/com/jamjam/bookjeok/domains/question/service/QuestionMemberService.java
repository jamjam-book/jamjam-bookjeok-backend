package com.jamjam.bookjeok.domains.question.service;

import com.jamjam.bookjeok.domains.question.dto.QuestionCategoryDTO;
import com.jamjam.bookjeok.domains.question.dto.QuestionDTO;
import com.jamjam.bookjeok.domains.question.dto.QuestionListDTO;
import com.jamjam.bookjeok.domains.question.dto.request.QuestionRequest;
import com.jamjam.bookjeok.domains.question.dto.response.QuestionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface QuestionMemberService {
    List<QuestionListDTO> findMemberQuestions( Map<String, Object> params);

    QuestionDTO findMemberQuestion(Map<String, Object> params);

    List<QuestionCategoryDTO> getQuestionCategories();

    QuestionResponse registQuestion(QuestionRequest request, MultipartFile questionImg);

    void validReceivedAnswer(Long questionId);

    QuestionResponse modifyQuestion(Long questionId, QuestionRequest request, MultipartFile questionImg);

    QuestionResponse deleteQuestion(Long questionId);
}
