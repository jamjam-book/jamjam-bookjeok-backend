package com.jamjam.bookjeok.domains.book.service;

import com.jamjam.bookjeok.common.service.FileStorageService;
import com.jamjam.bookjeok.domains.book.dto.*;
import com.jamjam.bookjeok.domains.book.dto.request.BookCategoryModifyRequest;
import com.jamjam.bookjeok.domains.book.dto.request.BookCategoryRequest;
import com.jamjam.bookjeok.domains.book.dto.request.BookRequest;
import com.jamjam.bookjeok.domains.book.dto.response.BookCategoryResponse;
import com.jamjam.bookjeok.domains.book.dto.response.BookResponse;
import com.jamjam.bookjeok.domains.book.entity.*;
import com.jamjam.bookjeok.domains.book.repository.*;
import com.jamjam.bookjeok.domains.book.repository.mapper.BookMapper;
import com.jamjam.bookjeok.exception.book.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookAdminServiceImpl implements BookAdminService {

    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final FileStorageService fileStorageService;
    private final BookMapper bookMapper;

    @Value("${image.image-url}")
    private String IMAGE_URL;

    @Override
    @Transactional
    public BookResponse registBook(BookRequest request, MultipartFile bookImg) {

        Optional<Book> findBook = bookRepository.findBookByIsbn(request.isbn());

        if(findBook.isPresent()) {
            throw new RegistPreexistingBookException(BookErrorCode.PREEXISTING_BOOK);
        }

        String replaceFileName = fileStorageService.storeFile(bookImg);

        Book book = Book.builder()
                .categoryId(request.categoryId())
                .publisherId(request.publisherId())
                .bookName(request.bookName())
                .isbn(request.isbn())
                .price(request.price())
                .publishedAt(request.publishedAt())
                .stockQuantity(request.stockQuantity())
                .imageUrl(replaceFileName)
                .bookInfo(request.bookInfo())
                .build();

        Book newBook = bookRepository.save(book);

        return buildBookResponse(newBook);

    }

    @Override
    @Transactional
    public BookResponse modifyBook(BookRequest request, MultipartFile bookImg) {

        Optional<Book> findBook = bookRepository.findBookByIsbn(request.isbn());

        if(findBook.isEmpty()) {
           throw new BookNotFoundException(BookErrorCode.NOT_EXIST_ISBN);
        }

        if(bookImg != null) {
            String replaceFileName = fileStorageService.storeFile(bookImg);
            String oldFileName = findBook.get().getImageUrl().replace(IMAGE_URL,"");
            fileStorageService.deleteFile(oldFileName);

            findBook.get().changeImageUrl(replaceFileName);
        }

        findBook.get().updateBook(
                request.publisherId(),
                request.categoryId(),
                request.bookName(),
                request.bookInfo(),
                request.isbn(),
                request.publishedAt(),
                request.price(),
                request.stockQuantity(),
                LocalDateTime.now()
        );

        Book modBook = bookRepository.save(findBook.get());
        log.info("{}", findBook.get());

        return buildBookResponse(modBook);

    }

    @Override
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
    @Transactional
    public List<BookDetailDTO> findBookListOrderByOption(Map<String, Object> params) {
        List<BookDetailDTO> books = bookMapper.findBookListOrderByOption(params);

        for (BookDetailDTO book : books) {
            if (book.getAuthorNames() != null) {
                List<AuthorDTO> authors = Arrays.stream(book.getAuthorNames().split(",\\s*"))
                        .map(AuthorDTO::new)
                        .toList();
            }
        }

        return books;
    }

    @Override
    public BookDetailDTO findBook(Map<String, String> params) {

        BookDetailDTO book =  bookMapper.findBookByIsbn(params);

        log.info("{}", book.toString());

        return book;

    }

    private BookResponse buildBookResponse(Book book) {

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
    public List<BookCategoryDTO> findBookCategory() {
        return bookMapper.findAllCategory();
    }

    @Override
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
