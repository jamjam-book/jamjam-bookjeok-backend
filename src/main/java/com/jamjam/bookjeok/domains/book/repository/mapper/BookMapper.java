package com.jamjam.bookjeok.domains.book.repository.mapper;

import com.jamjam.bookjeok.domains.book.dto.BookCategoryDTO;
import com.jamjam.bookjeok.domains.book.dto.BookDetailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BookMapper {

    List<BookDetailDTO> findBookListOrderByOption(Map<String, Object> params);

    List<BookCategoryDTO> findAllCategory();

    BookDetailDTO findBookByIsbn(Map<String, String> params);
}
