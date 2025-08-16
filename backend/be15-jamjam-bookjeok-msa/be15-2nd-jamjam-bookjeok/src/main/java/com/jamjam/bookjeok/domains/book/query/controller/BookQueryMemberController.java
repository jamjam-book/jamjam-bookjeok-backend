package com.jamjam.bookjeok.domains.book.query.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.book.query.dto.*;
import com.jamjam.bookjeok.domains.book.query.service.BookQueryMemberService;
import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BookQueryMemberController {

    private final BookQueryMemberService bookQueryMemberService;

    @GetMapping("/book/list")
    public ResponseEntity<ApiResponse<BookListPageDTO>> getBookList(@ModelAttribute BookSearchCondition condition) {

        log.info("params: {}", condition.toString());

        // 1. 도서 목록 조회
        List<BookDetailDTO> list = bookQueryMemberService.getBookList(condition);

        // 2. 전체 개수 조회
        int totalCount = bookQueryMemberService.getBookListCount(condition);
        boolean isLast = (condition.getOffset() + condition.getLimit()) >= totalCount;

        BookListPageDTO response = new BookListPageDTO(list, isLast);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("book/ca/list")
    public ResponseEntity<ApiResponse<List<BookCategoryDTO>>> selectCategory() {
        List<BookCategoryDTO> categories =  bookQueryMemberService.findBookCategory();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(categories));
    }

    @GetMapping("book/pricerange")
    public ResponseEntity<ApiResponse<PriceRangeDTO>> getPriceRange(@ModelAttribute BookSearchCondition condition) {

        PriceRangeDTO range = bookQueryMemberService.getPriceRange(condition);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(range));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse<BookDetailPageDTO>> getBookDetail(@PathVariable Long bookId) {

        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);

        BookDetailPageDTO book = bookQueryMemberService.getBookDetail(params);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(book));

    }

    @GetMapping("/book/{bookId}/reviews")
    public ResponseEntity<ApiResponse<ReviewListDTO>> getBookReviews(@PathVariable Long bookId, @Validated PageRequest pageRequest) {

        ReviewListDTO reviews = bookQueryMemberService.getBookReviews(bookId, pageRequest);

        return ResponseEntity.ok(ApiResponse.success(reviews));
    }

    @GetMapping("/book/popular")
    public ResponseEntity<ApiResponse<List<PopularBookDTO>>> getPopularBooks() {

        List<PopularBookDTO> books = bookQueryMemberService.getPopularBooks();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(books));

    }

    @GetMapping("/book/author/{authorId}")
    public ResponseEntity<ApiResponse<List<BookDetailDTO>>> getAuthorBooks(@PathVariable Long authorId) {

        Map<String, Object> params = new HashMap<>();
        params.put("authorId", authorId);
        List<BookDetailDTO> books = bookQueryMemberService.getAuthorBookList(params);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(books));
    }

    @PostMapping("/book/author/others")
    public ResponseEntity<ApiResponse<List<AuthorOtherBookDTO>>> getAuthorOtherBooks(@RequestBody AuthorOthersSearchCondition condition) {

        log.info("conditon, {}" , condition.toString());

        List<AuthorOtherBookDTO> others = bookQueryMemberService.getAuthorOtherBooks(condition);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(others));
    }

    @GetMapping("book/ca/main")
    public ResponseEntity<ApiResponse<List<BookCategoryDTO>>> selectMainCategories() {
        List<BookCategoryDTO> categories =  bookQueryMemberService.findMainCategories();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(categories));
    }
}
