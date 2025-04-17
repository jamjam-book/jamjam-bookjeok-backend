package com.jamjam.bookjeok.domains.book.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.book.command.dto.request.BookCategoryModifyRequest;
import com.jamjam.bookjeok.domains.book.command.dto.request.BookCategoryRequest;
import com.jamjam.bookjeok.domains.book.command.dto.request.BookRequest;
import com.jamjam.bookjeok.domains.book.command.dto.response.BookCategoryResponse;
import com.jamjam.bookjeok.domains.book.command.dto.response.BookResponse;
import com.jamjam.bookjeok.domains.book.command.entity.Book;
import com.jamjam.bookjeok.domains.book.command.service.BookCommandAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class BookCommandAdminController {

    private final BookCommandAdminService bookCommandService;

    @PostMapping("/book/in/{isbn}")
    public ResponseEntity<ApiResponse<BookResponse>> registBook(
            @RequestPart @Validated BookRequest bookRequest, @RequestPart MultipartFile bookImg) {
        // foundByIsbn 으로 null 값이면 새로 등록
        //Book foundBook = bookCommandService.findBookByIsbn(isbn);

        BookResponse bookResponse = bookCommandService.registBook(bookRequest, bookImg);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(bookResponse));
    }

    @PostMapping("/book/mod")
    public ResponseEntity<ApiResponse<BookResponse>> modifyBook (
            @RequestPart @Validated BookRequest bookRequest,
            @RequestPart MultipartFile bookImg) {

        BookResponse bookResponse = bookCommandService.modifyBook(bookRequest, bookImg);

        return ResponseEntity
                .ok(ApiResponse.success(bookResponse));
    }

    @PostMapping("/book/mod/q")
    public ResponseEntity<ApiResponse<BookResponse>> modifyStockQuantity (
            @RequestParam Long bookId, @RequestParam int quantity) {

        BookResponse bookResponse = bookCommandService.modifyStockQuantity(bookId, quantity);

        return ResponseEntity
                .ok(ApiResponse.success(bookResponse));
    }

    @DeleteMapping("/book/del")
    public ResponseEntity<ApiResponse<BookResponse>> deleteBook (@RequestParam Long bookId) {

        bookCommandService.deleteBook(bookId);

        return ResponseEntity
               .ok(ApiResponse.success(null));

    }

    @PostMapping("book/ca/in")
    public ResponseEntity<ApiResponse<BookCategoryResponse>> registCategory (@RequestBody @Validated BookCategoryRequest request) {

        BookCategoryResponse response = bookCommandService.registCategory(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }
    
    @PostMapping("/book/ca/mod")
    public ResponseEntity<ApiResponse<BookCategoryResponse>> modifyCategory(@RequestBody @Validated BookCategoryModifyRequest request) {

        BookCategoryResponse response = bookCommandService.modifyCategory(request);

        return ResponseEntity
                .ok(ApiResponse.success(response));
    }

    @DeleteMapping("/book/ca/del")
    public ResponseEntity<ApiResponse<BookCategoryResponse>> deleteCategory(@RequestBody @Validated BookCategoryRequest request) {

        bookCommandService.deleteCategory(request);

        return ResponseEntity
                .ok(ApiResponse.success(null));

    }
}
