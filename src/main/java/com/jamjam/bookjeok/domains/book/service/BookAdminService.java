package com.jamjam.bookjeok.domains.book.service;

import com.jamjam.bookjeok.domains.book.dto.BookCategoryDTO;
import com.jamjam.bookjeok.domains.book.dto.BookDetailDTO;
import com.jamjam.bookjeok.domains.book.dto.request.BookCategoryModifyRequest;
import com.jamjam.bookjeok.domains.book.dto.request.BookCategoryRequest;
import com.jamjam.bookjeok.domains.book.dto.request.BookRequest;
import com.jamjam.bookjeok.domains.book.dto.response.BookCategoryResponse;
import com.jamjam.bookjeok.domains.book.dto.response.BookResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BookAdminService {

    BookResponse registBook(BookRequest bookRequest, MultipartFile bookImg);

    List<BookDetailDTO> findBookListOrderByOption(Map<String, Object> params);

    void deleteBook(Long bookId);

    BookResponse modifyBook(BookRequest request, MultipartFile bookImg);

    BookCategoryResponse registCategory(BookCategoryRequest request);

    List<BookCategoryDTO> findBookCategory();

    BookCategoryResponse modifyCategory(BookCategoryModifyRequest request);

    void deleteCategory(BookCategoryRequest request);

    BookResponse modifyStockQuantity(Long bookId, int quantity);

    BookDetailDTO findBook(Map<String, String> params);
}
