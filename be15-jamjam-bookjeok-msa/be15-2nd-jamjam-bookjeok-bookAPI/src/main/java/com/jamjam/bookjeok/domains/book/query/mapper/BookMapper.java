package com.jamjam.bookjeok.domains.book.query.mapper;

import com.jamjam.bookjeok.domains.book.query.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BookMapper {

    List<BookDetailDTO> findBookListOrderByOption(Map<String, Object> params);

    List<BookCategoryDTO> findAllCategory();

    BookDetailDTO findBookByIsbn(Map<String, String> params);

}
