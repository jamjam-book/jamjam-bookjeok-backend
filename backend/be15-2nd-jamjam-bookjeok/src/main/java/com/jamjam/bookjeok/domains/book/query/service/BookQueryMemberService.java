package com.jamjam.bookjeok.domains.book.query.service;

import com.jamjam.bookjeok.domains.book.command.dto.request.ReviewRequest;
import com.jamjam.bookjeok.domains.book.query.dto.*;
import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;

import java.util.List;
import java.util.Map;

public interface BookQueryMemberService {

    List<BookDetailDTO> getBookList(BookSearchCondition condition);

    BookDetailPageDTO getBookDetail(Map<String, Object> params);

    List<PopularBookDTO> getPopularBooks();

    boolean validCheckBuyer(ReviewRequest reviewRequest);

    List<BookDetailDTO> getAuthorBookList(Map<String, Object> params);

    ReviewListDTO getBookReviews(Long bookId, PageRequest pageRequest);

    int getBookListCount(BookSearchCondition condition);

    List<BookCategoryDTO> findBookCategory();

    PriceRangeDTO getPriceRange(BookSearchCondition condition);

    List<AuthorOtherBookDTO> getAuthorOtherBooks(AuthorOthersSearchCondition condition);

    List<BookCategoryDTO> findMainCategories();
}
