package com.jamjam.bookjeok.domains.book.repository.mapper;

import com.jamjam.bookjeok.domains.book.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BookMapper {

    List<BookDetailDTO> findBookListOrderByOption(Map<String, Object> params);

    List<BookCategoryDTO> findAllCategory();

    BookDetailDTO findBookByIsbn(Map<String, String> params);

    BookDetailPageDTO getBookDetail(Map<String, Object> params);

    List<AuthorDTO> getAuthors(Map<String, Object> params);

    List<ReviewDTO> getReviews(Map<String, Object> params);

    List<PopularBookDTO> getPopularBooks();

    Long validCheckReviewer(Map<String, Object> params);
}
