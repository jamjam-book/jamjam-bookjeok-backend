package com.jamjam.bookjeok.domains.book.query.service;

import com.jamjam.bookjeok.domains.book.command.dto.request.ReviewRequest;
import com.jamjam.bookjeok.domains.book.query.dto.*;
import com.jamjam.bookjeok.domains.book.query.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookQueryMemberServiceImpl implements BookQueryMemberService {

    private final BookMapper bookMapper;

    @Override
    @Transactional
    public List<BookDetailDTO> getBookList(Map<String, Object> params) {

        List<BookDetailDTO> books = bookMapper.findBookListOrderByOption(params);

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
        params.put("bookId", book.getBookId());
        List<AuthorDTO> authors = bookMapper.getAuthors(params);
        List<ReviewDTO> reviews = bookMapper.getReviews(params);

        return new BookDetailPageDTO(
                book.getBookId(), book.getBookInfo(), book.getBookCategory(), book.getPublisher(),
                reviews, book.getBookName(), book.getIsbn(), book.getPublishedAt(), book.getPrice(),
                book.getStockQuantity(), book.getImageUrl(), authors, book.getInterestCount());

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
    public boolean validCheckBuyer(ReviewRequest request) {

        Map<String, Object> params = new HashMap<>();
        params.put("memberUid", request.memberUid());
        params.put("bookId", request.bookId());

        Long memberUid = bookMapper.validCheckReviewer(params);

        return memberUid != null;

    }

}
