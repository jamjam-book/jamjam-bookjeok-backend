package com.jamjam.bookjeok.domains.book.query.mapper;

import com.jamjam.bookjeok.domains.book.query.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BookMapper {

    List<BookDetailDTO> findBookListOrderByOption(BookSearchCondition params);

    List<BookCategoryDTO> findAllCategory();

    BookDetailPageDTO getBookDetail(Map<String, Object> params);

    List<AuthorDTO> getAuthors(Map<String, Object> params);

    List<ReviewDTO> getReviews(Map<String, Object> params);

    List<PopularBookDTO> getPopularBooks();

    Long validCheckReviewer(Map<String, Object> params);

    List<BookDetailDTO> getAuthorBooks(Map<String, Object> params);

    int getBookListCount(BookSearchCondition condition);

    List<BookCategoryDTO> findBookCategory();

    PriceRangeDTO getPriceRange(BookSearchCondition condition);

    List<AuthorOtherBookDTO> getAuthorOtherBooks(AuthorOthersSearchCondition condition);

    List<BookCategoryDTO> findMainCategories();
}
