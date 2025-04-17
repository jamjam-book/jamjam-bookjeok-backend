package com.jamjam.bookjeok.domains.book.query.service;


import com.jamjam.bookjeok.domains.book.query.dto.BookCategoryDTO;
import com.jamjam.bookjeok.domains.book.query.dto.BookDetailDTO;

import java.util.List;
import java.util.Map;

public interface BookQueryAdminService {

    List<BookDetailDTO> findBookListOrderByOption(Map<String, Object> params);

    List<BookCategoryDTO> findBookCategory();

    BookDetailDTO findBook(Map<String, String> params);
}
