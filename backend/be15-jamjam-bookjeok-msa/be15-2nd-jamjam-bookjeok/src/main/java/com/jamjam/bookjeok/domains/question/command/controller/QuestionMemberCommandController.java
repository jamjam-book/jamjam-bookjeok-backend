package com.jamjam.bookjeok.domains.question.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.question.command.dto.request.QuestionRequest;
import com.jamjam.bookjeok.domains.question.command.dto.response.QuestionResponse;
import com.jamjam.bookjeok.domains.question.command.service.QuestionMemberCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/{memberUid}")
public class QuestionMemberCommandController {

    private final QuestionMemberCommandService questionMemberCommandService;

    @PostMapping("/question/in")
    public ResponseEntity<ApiResponse<QuestionResponse>> registQuestion(
            @RequestPart @Validated QuestionRequest request,
            @RequestPart MultipartFile questionImg) {

        QuestionResponse response = questionMemberCommandService.registQuestion(request, questionImg);

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

        QuestionResponse response = questionMemberCommandService.modifyQuestion(questionId, request, questionImg);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/question/del/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponse>> deleteQuestion(
            @PathVariable Long questionId) {

        QuestionResponse response = questionMemberCommandService.deleteQuestion(questionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));

    }

    private void validReceivedAnswer(Long questionId) {

        questionMemberCommandService.validReceivedAnswer(questionId);

    }


}
