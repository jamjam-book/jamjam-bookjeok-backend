package com.jamjam.bookjeok.domains.book.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.book.dto.BookCategoryDTO;
import com.jamjam.bookjeok.domains.book.dto.BookDetailDTO;
import com.jamjam.bookjeok.domains.book.dto.request.BookCategoryModifyRequest;
import com.jamjam.bookjeok.domains.book.dto.request.BookCategoryRequest;
import com.jamjam.bookjeok.domains.book.dto.request.BookRequest;
import com.jamjam.bookjeok.domains.book.dto.response.BookCategoryResponse;
import com.jamjam.bookjeok.domains.book.dto.response.BookResponse;
import com.jamjam.bookjeok.domains.book.service.BookAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class BookAdminController {

    private final BookAdminService bookAdminService;

    @PostMapping("/book/in")
    public ResponseEntity<ApiResponse<BookResponse>> registBook(
            @RequestPart @Validated BookRequest bookRequest,
            @RequestPart MultipartFile bookImg) {
        BookResponse bookResponse = bookAdminService.registBook(bookRequest, bookImg);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(bookResponse));
    }

    @PostMapping("/book/mod")
    public ResponseEntity<ApiResponse<BookResponse>> modifyBook (
            @RequestPart @Validated BookRequest bookRequest,
            @RequestPart MultipartFile bookImg) {

        BookResponse bookResponse = bookAdminService.modifyBook(bookRequest, bookImg);

        return ResponseEntity
                .ok(ApiResponse.success(bookResponse));
    }

    @PostMapping("/book/mod/q")
    public ResponseEntity<ApiResponse<BookResponse>> modifyStockQuantity (
            @RequestParam Long bookId, @RequestParam int quantity) {

        BookResponse bookResponse = bookAdminService.modifyStockQuantity(bookId, quantity);

        return ResponseEntity
                .ok(ApiResponse.success(bookResponse));
    }

    @DeleteMapping("/book/del")
    public ResponseEntity<ApiResponse<BookResponse>> deleteBook (@RequestParam Long bookId) {

        bookAdminService.deleteBook(bookId);

        return ResponseEntity
               .ok(ApiResponse.success(null));

    }

    @GetMapping("/book/list")
    public List<BookDetailDTO> selectBook(@RequestParam Map<String, Object> params) {

        return bookAdminService.findBookListOrderByOption(params);
    }

    @GetMapping("/book/{isbn}")
    public BookDetailDTO selectBookDetail(@PathVariable String isbn) {

        Map<String, String> params = new HashMap<>();
        params.put("isbn", isbn);

        return bookAdminService.findBook(params);
    }

    @GetMapping("book/ca/list")
    public List<BookCategoryDTO> selectCategory() {
        return bookAdminService.findBookCategory();
    }

    @PostMapping("book/ca/in")
    public ResponseEntity<ApiResponse<BookCategoryResponse>> registCategory (@RequestBody @Validated BookCategoryRequest request) {

        BookCategoryResponse response = bookAdminService.registCategory(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }
    
    @PostMapping("/book/ca/mod")
    public ResponseEntity<ApiResponse<BookCategoryResponse>> modifyCategory(@RequestBody @Validated BookCategoryModifyRequest request) {

        BookCategoryResponse response = bookAdminService.modifyCategory(request);

        return ResponseEntity
                .ok(ApiResponse.success(response));
    }

    @DeleteMapping("/book/ca/del")
    public ResponseEntity<ApiResponse<BookCategoryResponse>> deleteCategory(@RequestBody @Validated BookCategoryRequest request) {

        bookAdminService.deleteCategory(request);

        return ResponseEntity
                .ok(ApiResponse.success(null));

    }
}
