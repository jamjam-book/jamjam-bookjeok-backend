package com.jamjam.bookjeok.domains.member.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.command.service.InterestBookCommandService;
import com.jamjam.bookjeok.domains.member.command.dto.request.InterestBookRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.InterestBookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InterestBookCommandController {

    private final InterestBookCommandService interestBookCommandService;

    @PostMapping("/members/interest/books")
    public ResponseEntity<ApiResponse<InterestBookResponse>> createInterestBook(
            @RequestBody @Validated InterestBookRequest interestBookRequest
    ){
        String bookName = interestBookCommandService.createInterestBook(interestBookRequest);

        InterestBookResponse response = InterestBookResponse.builder()
                .bookName(bookName)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/members/{memberId}/interest/books/{bookId}")
    public ResponseEntity<ApiResponse<Void>> deleteInterestBook(
            @PathVariable String memberId,
            @PathVariable Long bookId
    ){

        interestBookCommandService.deleteInterestBook(memberId, bookId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

}