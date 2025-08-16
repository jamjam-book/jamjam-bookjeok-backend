package com.jamjam.bookjeok.domains.book.command.service;

import com.jamjam.bookjeok.domains.book.command.dto.request.BookCategoryModifyRequest;
import com.jamjam.bookjeok.domains.book.command.dto.request.BookCategoryRequest;
import com.jamjam.bookjeok.domains.book.command.dto.response.BookCategoryResponse;
import com.jamjam.bookjeok.domains.book.command.dto.response.BookResponse;
import com.jamjam.bookjeok.domains.book.command.entity.Book;

import java.util.Optional;

public interface BookCommandAdminService {

    void deleteBook(Long bookId);

    BookCategoryResponse registCategory(BookCategoryRequest request);

    BookCategoryResponse modifyCategory(BookCategoryModifyRequest request);

    void deleteCategory(BookCategoryRequest request);

    BookResponse modifyStockQuantity(Long bookId, int quantity);

    Optional<Book> findBookByIsbn(String isbn);

    BookResponse buildBookResponse(Book book);

    BookResponse findBookByBookId(Long bookId);
}
