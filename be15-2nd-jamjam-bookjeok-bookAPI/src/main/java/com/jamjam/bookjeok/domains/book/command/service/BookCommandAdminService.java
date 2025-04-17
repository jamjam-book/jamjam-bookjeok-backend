package com.jamjam.bookjeok.domains.book.command.service;

import com.jamjam.bookjeok.domains.book.command.dto.request.BookCategoryModifyRequest;
import com.jamjam.bookjeok.domains.book.command.dto.request.BookCategoryRequest;
import com.jamjam.bookjeok.domains.book.command.dto.request.BookRequest;
import com.jamjam.bookjeok.domains.book.command.dto.response.BookCategoryResponse;
import com.jamjam.bookjeok.domains.book.command.dto.response.BookResponse;
import com.jamjam.bookjeok.domains.book.command.entity.Book;
import org.springframework.web.multipart.MultipartFile;

public interface BookCommandAdminService {

    BookResponse registBook(BookRequest bookRequest, MultipartFile bookImg);

    void deleteBook(Long bookId);

    BookResponse modifyBook(BookRequest request, MultipartFile bookImg);

    BookCategoryResponse registCategory(BookCategoryRequest request);

    BookCategoryResponse modifyCategory(BookCategoryModifyRequest request);

    void deleteCategory(BookCategoryRequest request);

    BookResponse modifyStockQuantity(Long bookId, int quantity);

    Book findBookByIsbn(String isbn);
}
