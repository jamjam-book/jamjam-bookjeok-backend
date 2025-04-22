package com.jamjam.bookjeok.domains.book.command.service;

import com.jamjam.bookjeok.domains.book.command.dto.BookApiDTO;
import com.jamjam.bookjeok.domains.book.command.entity.Book;
import com.jamjam.bookjeok.domains.book.command.entity.BookCategory;
import com.jamjam.bookjeok.domains.book.command.entity.Publisher;
import com.jamjam.bookjeok.domains.book.query.dto.BookDTO;

import java.util.concurrent.CompletableFuture;

public interface BookApiService {

    Publisher findPublisher(String publisher);

    BookCategory findCategoryByCategoryName(String categoryNameStr);

    void registAuthor(String[] authors, Long bookId);

    void registBookAuthor(Long bookId, Long authorId);

    BookApiDTO getBookByIsbn(String isbn);
}
