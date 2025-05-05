package com.jamjam.bookjeok.domains.book.query.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.book.query.dto.BookCategoryDTO;
import com.jamjam.bookjeok.domains.book.query.dto.BookDetailDTO;
import com.jamjam.bookjeok.domains.book.query.service.BookQueryAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class BookQueryAdminController {

    private final BookQueryAdminService bookQueryAdminService;

    @GetMapping("/book/list")
    public ResponseEntity<ApiResponse<Page<BookDetailDTO>>> selectBook(@RequestParam Map<String, Object> params,
                                                                       @RequestParam(name="page", defaultValue = "0") int page,
                                                                       @RequestParam(name="size", defaultValue = "10") int size) {
        log.info("params: {}", params);
        log.info("page: {}, size: {}", page, size);

        if (params.containsKey("isDeleted")) {
            Object value = params.get("isDeleted");
            if (value instanceof String str) {
                if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")) {
                    params.put("isDeleted", Boolean.valueOf(str));
                }
            }
        }

        if (params.containsKey("category")) {
            Object value = params.get("category");
            if (value instanceof String str && !str.isBlank()) {
                try {
                    params.put("category", Long.parseLong(str));
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<BookDetailDTO> books = bookQueryAdminService.findBookListOrderByOption(params, pageRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(books));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse<BookDetailDTO>> selectBookDetail(@PathVariable(name="bookId") Long bookId) {

        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);

        BookDetailDTO book = bookQueryAdminService.findBook(params);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(book));
    }

    @GetMapping("book/ca/list")
    public ResponseEntity<ApiResponse<List<BookCategoryDTO>>> selectCategory() {
        List<BookCategoryDTO> categories =  bookQueryAdminService.findBookCategory();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(categories));
    }

}
