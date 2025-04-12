package com.jamjam.bookjeok.domains.book.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.book.dto.BookDetailDTO;
import com.jamjam.bookjeok.domains.book.dto.BookDetailPageDTO;
import com.jamjam.bookjeok.domains.book.dto.PopularBookDTO;
import com.jamjam.bookjeok.domains.book.dto.request.ReviewRequest;
import com.jamjam.bookjeok.domains.book.dto.response.ReviewResponse;
import com.jamjam.bookjeok.domains.book.service.BookMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BookMemberController {

    private final BookMemberService bookMemberService;

    @GetMapping("/book/list")
    public ResponseEntity<ApiResponse< List<BookDetailDTO>>> getBookList(@RequestParam Map<String, Object> params) {

        List<BookDetailDTO> list =  bookMemberService.getBookList(params);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(list));
    }

    @GetMapping("/book/{isbn}")
    public ResponseEntity<ApiResponse<BookDetailPageDTO>> getBookDetail(@PathVariable String isbn, Map<String, Object> params) {

        params.put("isbn", isbn);

        BookDetailPageDTO book = bookMemberService.getBookDetail(params);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(book));

    }

    @GetMapping("/book/popular")
    public ResponseEntity<ApiResponse<List<PopularBookDTO>>> getPopularBooks() {

        List<PopularBookDTO> books = bookMemberService.getPopularBooks();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(books));

    }

    @PostMapping("/book/{isbn}/review")
    public ResponseEntity<ApiResponse<ReviewResponse>> writeReview(@RequestBody @Validated ReviewRequest reviewRequest) {
        
        boolean isBuyer = bookMemberService.validCheckBuyer(reviewRequest);

        ReviewResponse response = bookMemberService.writeReview(reviewRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @PutMapping("/book/{isbn}/review/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> modifyReview(
            @RequestBody @Validated ReviewRequest reviewRequest,
            @PathVariable Long reviewId){

        ReviewResponse response = bookMemberService.modifyReview(reviewRequest, reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/book/{isbn}/review/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long reviewId) {

        bookMemberService.deleteReview(reviewId);

        return ResponseEntity
                .ok(ApiResponse.success(null));

    }

}
