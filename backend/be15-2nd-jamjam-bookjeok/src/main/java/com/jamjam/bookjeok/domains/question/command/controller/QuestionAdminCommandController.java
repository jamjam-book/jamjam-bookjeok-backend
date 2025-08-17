package com.jamjam.bookjeok.domains.question.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.question.command.dto.response.QuestionAnswerResponse;
import com.jamjam.bookjeok.domains.question.command.service.QuestionAdminCommandService;
import com.jamjam.bookjeok.domains.question.command.dto.request.QuestionAnswerRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class QuestionAdminCommandController {

    private final QuestionAdminCommandService questionAdminCommandService;

    @PostMapping("/question/{questionId}")
    public ResponseEntity<ApiResponse<QuestionAnswerResponse>> registQuestionAnswer(
            @RequestBody @Valid QuestionAnswerRequest request) {

        QuestionAnswerResponse response = questionAdminCommandService.registQuestionAnswer(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));

    }

    @PutMapping("/question/{questionId}/{answerId}")
    public ResponseEntity<ApiResponse<QuestionAnswerResponse>> modifyQuestionAnswer(
            @PathVariable(name = "answerId") Long answerId,
            @RequestBody @Valid QuestionAnswerRequest request) {

        QuestionAnswerResponse response = questionAdminCommandService.modifyQuestionAnswer(answerId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/question/{questionId}/{answerId}")
    public ResponseEntity<ApiResponse<QuestionAnswerResponse>> deleteQuestionAnswer(@PathVariable(name = "answerId") Long answerId) {

        QuestionAnswerResponse response = questionAdminCommandService.deleteQuestionAnswer(answerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }
}
