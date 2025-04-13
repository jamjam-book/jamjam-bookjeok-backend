package com.jamjam.bookjeok.domains.question.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.question.dto.QuestionCategoryDTO;
import com.jamjam.bookjeok.domains.question.dto.QuestionDTO;
import com.jamjam.bookjeok.domains.question.dto.QuestionListDTO;
import com.jamjam.bookjeok.domains.question.dto.request.QuestionRequest;
import com.jamjam.bookjeok.domains.question.dto.response.QuestionResponse;
import com.jamjam.bookjeok.domains.question.service.QuestionMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/{memberUid}")
public class QuestionMemberController {

    private final QuestionMemberService questionMemberService;

    @GetMapping("/question")
    public ResponseEntity<ApiResponse<List<QuestionListDTO>>> findMemberQuestions(@PathVariable Long memberUid) {

        Map<String, Object> params  = new HashMap<>();
        params.put("memberUid", memberUid);
        List<QuestionListDTO> questions = questionMemberService.findMemberQuestions(params);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(questions));
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<ApiResponse<QuestionDTO>> findMemberQuestionDetail(@PathVariable Long memberUid, @PathVariable Long questionId) {

        Map<String, Object> params  = new HashMap<>();
        params.put("memberUid", memberUid);
        params.put("questionId", questionId);
        QuestionDTO question = questionMemberService.findMemberQuestion(params);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(question));

    }

    @GetMapping("/question/in")
    public ResponseEntity<ApiResponse<List<QuestionCategoryDTO>>> getQuestionInsertPage() {

        List<QuestionCategoryDTO> categories = getQuestionCategories();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(categories));

    }

    @PostMapping("/question/in")
    public ResponseEntity<ApiResponse<QuestionResponse>> registQuestion(
            @RequestPart @Validated QuestionRequest request,
            @RequestPart MultipartFile questionImg) {

        QuestionResponse response = questionMemberService.registQuestion(request, questionImg);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @PutMapping("/question/mod/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponse>> modifyQuestion(
            @PathVariable Long questionId,
            @RequestPart @Validated QuestionRequest request,
            @RequestPart MultipartFile questionImg) {

        validReceivedAnswer(questionId);

        QuestionResponse response = questionMemberService.modifyQuestion(questionId, request, questionImg);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/question/del/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponse>> deleteQuestion(
            @PathVariable Long questionId) {

        QuestionResponse response = questionMemberService.deleteQuestion(questionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));

    }

    private  List<QuestionCategoryDTO> getQuestionCategories() {

        return questionMemberService.getQuestionCategories();
    }

    private void validReceivedAnswer(Long questionId) {

        questionMemberService.validReceivedAnswer(questionId);

    }

}
