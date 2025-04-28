package com.jamjam.bookjeok.domains.book.query.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.book.query.dto.BookDetailDTO;
import com.jamjam.bookjeok.domains.book.query.dto.BookDetailPageDTO;
import com.jamjam.bookjeok.domains.book.query.dto.PopularBookDTO;
import com.jamjam.bookjeok.domains.book.query.service.BookQueryMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BookQueryMemberController {

    private final BookQueryMemberService bookQueryMemberService;

    @GetMapping("/book/list")
    public ResponseEntity<ApiResponse< List<BookDetailDTO>>> getBookList(@RequestParam Map<String, Object> params) {

        List<BookDetailDTO> list =  bookQueryMemberService.getBookList(params);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(list));
    }

    @GetMapping("/book/{isbn}")
    public ResponseEntity<ApiResponse<BookDetailPageDTO>> getBookDetail(@PathVariable String isbn, Map<String, Object> params) {

        params.put("isbn", isbn);

        BookDetailPageDTO book = bookQueryMemberService.getBookDetail(params);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(book));

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
}
