package com.jamjam.bookjeok.domains.book.service;

import com.jamjam.bookjeok.domains.book.dto.BookApiDTO;
import com.jamjam.bookjeok.domains.book.dto.BookDTO;
import com.jamjam.bookjeok.domains.book.entity.Book;
import com.jamjam.bookjeok.domains.book.entity.BookCategory;
import com.jamjam.bookjeok.domains.book.entity.Publisher;

import java.util.concurrent.CompletableFuture;

public interface BookApiService {

    CompletableFuture<String> addTocAndCategoryName(BookApiDTO book);

    Publisher findPublisher(String publisher);

    BookCategory findCategoryByCategoryName(String categoryNameStr);

    Book registBook(BookDTO bookDTO);

    void registAuthor(String[] authors, Long bookId);

    String saveFile(String imgUrl);

    void registBookAuthor(Long bookId, Long authorId);
}
