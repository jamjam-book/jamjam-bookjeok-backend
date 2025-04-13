package com.jamjam.bookjeok.domains.question.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.question.dto.QuestionListDTO;
import com.jamjam.bookjeok.domains.question.dto.request.QuestionAnswerRequest;
import com.jamjam.bookjeok.domains.question.dto.response.QuestionAnswerResponse;
import com.jamjam.bookjeok.domains.question.service.QuestionAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class QuestionAdminController {

    private final QuestionAdminService questionAdminService;

    @GetMapping("/question")
    public ResponseEntity<ApiResponse<List<QuestionListDTO>>> findQuestions(@RequestParam Map<String, Object> params) {

        List<QuestionListDTO> questions = questionAdminService.findQuestions(params);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(questions));
    }

    @PostMapping("/question/{questionId}")
    public ResponseEntity<ApiResponse<QuestionAnswerResponse>> registQuestionAnswer(
            @RequestBody @Valid QuestionAnswerRequest request) {

        QuestionAnswerResponse response = questionAdminService.registQuestionAnswer(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));

    }

    @PutMapping("/question/{questionId}/{answerId}")
    public ResponseEntity<ApiResponse<QuestionAnswerResponse>> modifyQuestionAnswer(
            @PathVariable(name = "answerId") Long answerId,
            @RequestBody @Valid QuestionAnswerRequest request) {

        QuestionAnswerResponse response = questionAdminService.modifyQuestionAnswer(answerId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/question/{questionId}/{answerId}")
    public ResponseEntity<ApiResponse<QuestionAnswerResponse>> deleteQuestionAnswer(@PathVariable(name = "answerId") Long answerId) {

        QuestionAnswerResponse response = questionAdminService.deleteQuestionAnswer(answerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }
}
