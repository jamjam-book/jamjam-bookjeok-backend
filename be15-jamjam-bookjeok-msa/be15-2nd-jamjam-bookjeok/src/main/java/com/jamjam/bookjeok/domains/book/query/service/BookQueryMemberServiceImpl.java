package com.jamjam.bookjeok.domains.book.query.service;

import com.jamjam.bookjeok.common.dto.Pagination;
import com.jamjam.bookjeok.domains.book.command.dto.request.ReviewRequest;
import com.jamjam.bookjeok.domains.book.query.dto.*;
import com.jamjam.bookjeok.domains.book.query.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookQueryMemberServiceImpl implements BookQueryMemberService {

    private final BookMapper bookMapper;

    @Override
    @Transactional
    public List<BookDetailDTO> getBookList(BookSearchCondition condition) {

        List<BookDetailDTO> books = bookMapper.findBookListOrderByOption(condition);

        for (BookDetailDTO book : books) {
            List<AuthorDTO> authors = new ArrayList<>();
            if (book.getAuthorNames() != null) {
                authors = Arrays.stream(book.getAuthorNames().split(",\\s*"))
                        .map(AuthorDTO::new)
                        .toList();
            }
            book.addList(authors);
        }

        return books;
    }

    @Override
    @Transactional
    public BookDetailPageDTO getBookDetail(Map<String, Object> params) {

        BookDetailPageDTO book =  bookMapper.getBookDetail(params);
        List<AuthorDTO> authors = bookMapper.getAuthors(params);

        return new BookDetailPageDTO(
                book.getBookId(), book.getBookInfo(), book.getBookCategory(), book.getPublisher(),
                book.getBookName(), book.getIsbn(), book.getPublishedAt(), book.getPrice(),
                book.getStockQuantity(), book.getImageUrl(), authors, book.getInterestCount());

    }

    @Override
    @Transactional
    public ReviewListDTO getBookReviews(Long bookId, PageRequest pageRequest) {

        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);
        params.put("pageRequest", pageRequest);

        List<ReviewDTO> reviews = bookMapper.getReviews(params);

        long totalReviews = reviews.size();

        int page = pageRequest.getPage();
        int size = pageRequest.getSize();

        return ReviewListDTO.builder()
                .reviews(reviews)
                .pagination(Pagination.builder()
                        .currentPage(page)
                        .totalPage((int)Math.ceil((double)totalReviews/size))
                        .totalItems(totalReviews)
                        .build())
                .build();
    }

    @Override
    public int getBookListCount(BookSearchCondition condition) {
        return bookMapper.getBookListCount(condition);
    }

    @Override
    public List<BookCategoryDTO> findBookCategory() {
        return bookMapper.findBookCategory();
    }

    @Override
    public PriceRangeDTO getPriceRange(BookSearchCondition condition) {
        return bookMapper.getPriceRange(condition);
    }

    @Override
    public List<AuthorOtherBookDTO> getAuthorOtherBooks(AuthorOthersSearchCondition condition) {
        return bookMapper.getAuthorOtherBooks(condition);
    }

    @Override
    @Transactional
    public List<PopularBookDTO> getPopularBooks() {
        List<PopularBookDTO> books = bookMapper.getPopularBooks();
        Map<String, Object> params = new HashMap<>();

        List<PopularBookDTO> result = new ArrayList<>();

        for (PopularBookDTO book : books) {

            params.put("bookId", book.getBookId());
            List<AuthorDTO> authors = bookMapper.getAuthors(params);

            result.add(new PopularBookDTO(
                    book.getBookId(), book.getBookName(),
                    book.getImageUrl(), book.getIsbn(),book.getPublishedAt(),
                    book.getTotalQuantity(), book.getPublisher(), authors
            ));
        }

        return result;
    }

    @Override
    @Transactional
    public List<BookDetailDTO> getAuthorBookList(Map<String, Object> params) {
        List<BookDetailDTO> books = bookMapper.getAuthorBooks(params);

        for (BookDetailDTO book : books) {
            List<AuthorDTO> authors = new ArrayList<>();
            if (book.getAuthorNames() != null) {
                authors = Arrays.stream(book.getAuthorNames().split(",\\s*"))
                        .map(AuthorDTO::new)
                        .toList();
            }
            book.addList(authors);
        }

        return books;
    }

    @Override
    @Transactional
    public boolean validCheckBuyer(ReviewRequest request) {

        Map<String, Object> params = new HashMap<>();
        params.put("memberUid", request.memberUid());
        params.put("bookId", request.bookId());

        Long memberUid = bookMapper.validCheckReviewer(params);

        return memberUid != null;

    }
}
