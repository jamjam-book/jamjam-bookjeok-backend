package com.jamjam.bookjeok.domains.question.query.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.question.query.dto.QuestionDTO;
import com.jamjam.bookjeok.domains.question.query.dto.QuestionListDTO;
import com.jamjam.bookjeok.domains.question.query.service.QuestionAdminQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class QuestionAdminQueryController {

    private final QuestionAdminQueryService questionAdminQueryService;

    @GetMapping("/question")
    public ResponseEntity<ApiResponse<List<QuestionListDTO>>> findQuestions(@RequestParam Map<String, Object> params) {

        List<QuestionListDTO> questions = questionAdminQueryService.findQuestions(params);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(questions));
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<ApiResponse<QuestionDTO>> findQuestionByQuestionId(
            @PathVariable Long questionId) {

        Map<String, Object> params  = new HashMap<>();
        params.put("questionId", questionId);
        QuestionDTO question = questionAdminQueryService.findQuestionByQuestionId(params);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(question));

    }
}
