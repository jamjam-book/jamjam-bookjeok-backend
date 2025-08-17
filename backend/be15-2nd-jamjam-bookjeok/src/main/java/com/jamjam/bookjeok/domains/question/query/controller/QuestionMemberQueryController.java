package com.jamjam.bookjeok.domains.question.query.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.question.query.dto.QuestionCategoryDTO;
import com.jamjam.bookjeok.domains.question.query.dto.QuestionDTO;
import com.jamjam.bookjeok.domains.question.query.dto.QuestionListDTO;
import com.jamjam.bookjeok.domains.question.query.service.QuestionMemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/{memberUid}")
public class QuestionMemberQueryController {

    private final QuestionMemberQueryService questionMemberQueryService;

    @GetMapping("/question")
    public ResponseEntity<ApiResponse<List<QuestionListDTO>>> findMemberQuestions(@PathVariable Long memberUid) {

        Map<String, Object> params  = new HashMap<>();
        params.put("memberUid", memberUid);
        List<QuestionListDTO> questions = questionMemberQueryService.findMemberQuestions(params);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(questions));
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<ApiResponse<QuestionDTO>> findMemberQuestionDetail(@PathVariable Long memberUid, @PathVariable Long questionId) {

        Map<String, Object> params  = new HashMap<>();
        params.put("memberUid", memberUid);
        params.put("questionId", questionId);
        QuestionDTO question = questionMemberQueryService.findMemberQuestion(params);

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

    private  List<QuestionCategoryDTO> getQuestionCategories() {

        return questionMemberQueryService.getQuestionCategories();
    }

}
