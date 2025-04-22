package com.jamjam.bookjeok.domains.book.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.book.command.dto.BookApiDTO;
import com.jamjam.bookjeok.domains.book.command.dto.request.BookCategoryModifyRequest;
import com.jamjam.bookjeok.domains.book.command.dto.request.BookCategoryRequest;
import com.jamjam.bookjeok.domains.book.command.dto.response.BookCategoryResponse;
import com.jamjam.bookjeok.domains.book.command.dto.response.BookResponse;
import com.jamjam.bookjeok.domains.book.command.entity.Book;
import com.jamjam.bookjeok.domains.book.command.service.BookApiService;
import com.jamjam.bookjeok.domains.book.command.service.BookCommandAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class BookCommandAdminController {

    private final BookCommandAdminService bookCommandService;
    private final BookApiService bookApiService;

    @PostMapping("/book/check/{isbn}")
    public ResponseEntity<ApiResponse<?>> checkBookByIsbn(
            @PathVariable(name="isbn") String isbn) {

        Optional<Book> foundBook = bookCommandService.findBookByIsbn(isbn);

        if(foundBook.isPresent()) {
             return ResponseEntity.ok(ApiResponse.success(Map.of("status", "exists", "bookId", foundBook.get().getBookId())));
        } else {

            BookApiDTO apiBook = bookApiService.getBookByIsbn(isbn);
            return ResponseEntity.ok(ApiResponse.success(Map.of("status", "new", "book", apiBook)));
        }
    }

    @GetMapping("/book/mod/{bookId}")
    public ResponseEntity<ApiResponse<BookResponse>> modifyBookPage(
            @PathVariable(name="bookId") Long bookId
    ) {
        BookResponse response = bookCommandService.findBookByBookId(bookId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/book/mod/{bookId}")
    public ResponseEntity<ApiResponse<BookResponse>> modifyStockQuantity (
            @PathVariable(name="bookId") Long bookId, @RequestParam int quantity) {

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
