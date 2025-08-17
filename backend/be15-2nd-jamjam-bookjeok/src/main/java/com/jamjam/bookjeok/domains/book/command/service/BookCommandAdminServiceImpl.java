package com.jamjam.bookjeok.domains.book.command.service;

import com.jamjam.bookjeok.domains.book.command.dto.request.BookCategoryModifyRequest;
import com.jamjam.bookjeok.domains.book.command.dto.request.BookCategoryRequest;
import com.jamjam.bookjeok.domains.book.command.dto.response.BookCategoryResponse;
import com.jamjam.bookjeok.domains.book.command.dto.response.BookResponse;
import com.jamjam.bookjeok.domains.book.command.entity.Book;
import com.jamjam.bookjeok.domains.book.command.entity.BookCategory;
import com.jamjam.bookjeok.domains.book.command.repository.BookCategoryRepository;
import com.jamjam.bookjeok.domains.book.command.repository.BookRepository;
import com.jamjam.bookjeok.exception.book.BookErrorCode;
import com.jamjam.bookjeok.exception.book.BookNotFoundException;
import com.jamjam.bookjeok.exception.book.category.BookCategoryNotFoundException;
import com.jamjam.bookjeok.exception.book.category.RegistPreexistingCategoryException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookCommandAdminServiceImpl implements BookCommandAdminService {

    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;

    @Override
    @Transactional
    public BookResponse modifyStockQuantity(Long bookId, int quantity) {

        Optional<Book> findBook = bookRepository.findBookByBookId(bookId);

        if(findBook.isEmpty()) {
            throw new BookNotFoundException(BookErrorCode.NOT_EXIST_BOOK);
        }

        findBook.get().updateStockQuantity(quantity, LocalDateTime.now());
        Book modQuantity = bookRepository.save(findBook.get());

        log.info("{}", findBook.get().getStockQuantity());

        return buildBookResponse(modQuantity);

    }

    @Override
    public Optional<Book> findBookByIsbn(String isbn) {
        return bookRepository.findBookByIsbn(isbn);
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {

        Optional<Book> findBook = bookRepository.findBookByBookId(bookId);

        if(findBook.isEmpty()) {
            throw new BookNotFoundException(BookErrorCode.NOT_EXIST_BOOK);
        }

        findBook.get().deleteBook(LocalDateTime.now(), true);
        bookRepository.save(findBook.get());
    }

    @Override
    public BookResponse buildBookResponse(Book book) {

        return BookResponse.builder()
                .categoryId(book.getCategoryId())
                .publisherId(book.getPublisherId())
                .bookName(book.getBookName())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .publishedAt(book.getPublishedAt())
                .stockQuantity(book.getStockQuantity())
                .imageUrl(book.getImageUrl())
                .bookInfo(book.getBookInfo())
                .build();
    }

    @Override
    public BookResponse findBookByBookId(Long bookId) {

        Optional<Book> findBook = bookRepository.findBookByBookId(bookId);

        if(findBook.isEmpty()) {
            throw new BookNotFoundException(BookErrorCode.NOT_EXIST_BOOK);
        }

        return buildBookResponse(findBook.get());
    }


    @Override
    @Transactional
    public BookCategoryResponse registCategory(BookCategoryRequest request) {

        Optional<BookCategory> findCategory = bookCategoryRepository.findCategoryByCategoryName(request.categoryName());

        if(findCategory.isPresent()) {
            throw new RegistPreexistingCategoryException(BookErrorCode.PREEXISTING_CATEGORY);
        }

        BookCategory bookCategory = BookCategory.builder()
                .categoryName(request.categoryName())
                .build();

        BookCategory newCategory = bookCategoryRepository.save(bookCategory);

        return buildBookCategoryResponse(newCategory);
    }


    @Override
    @Transactional
    public BookCategoryResponse modifyCategory(BookCategoryModifyRequest request) {
        Optional<BookCategory> findCategory = bookCategoryRepository.findCategoryByCategoryName(request.categoryName());

        if(findCategory.isEmpty()) {
            throw new BookCategoryNotFoundException(BookErrorCode.NOT_FOUND_CATEGORY);
        }

        Optional<BookCategory> newCategory = bookCategoryRepository.findCategoryByCategoryName(request.newCategoryName());

        if(newCategory.isPresent()) {
            throw new RegistPreexistingCategoryException(BookErrorCode.PREEXISTING_CATEGORY);
        }

        findCategory.get().updateCategory(
                request.newCategoryName(),LocalDateTime.now()
        );

        BookCategory modCategory = bookCategoryRepository.save(findCategory.get());

        log.info("{}", findCategory.get());

        return buildBookCategoryResponse(modCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(BookCategoryRequest request) {

        Optional<BookCategory> findCategory = bookCategoryRepository.findCategoryByCategoryName(request.categoryName());

        if(findCategory.isEmpty()) {
            throw new BookCategoryNotFoundException(BookErrorCode.NOT_FOUND_CATEGORY);
        }

        findCategory.get().deleteCategory(LocalDateTime.now(), true);
        bookCategoryRepository.save(findCategory.get());

    }

    private BookCategoryResponse buildBookCategoryResponse(BookCategory category) {

        return BookCategoryResponse.builder().categoryName(category.getCategoryName()).build();
    }

}
