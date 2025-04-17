package com.jamjam.bookjeok.domains.book.command.service;

import com.jamjam.bookjeok.domains.book.command.dto.BookApiDTO;
import com.jamjam.bookjeok.domains.book.command.entity.Book;
import com.jamjam.bookjeok.domains.book.command.entity.BookCategory;
import com.jamjam.bookjeok.domains.book.command.entity.Publisher;
import com.jamjam.bookjeok.domains.book.query.dto.BookDTO;

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
