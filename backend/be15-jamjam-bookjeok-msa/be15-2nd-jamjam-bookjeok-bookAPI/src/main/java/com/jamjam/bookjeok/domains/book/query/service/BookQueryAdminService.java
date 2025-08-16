package com.jamjam.bookjeok.domains.book.query.service;


import com.jamjam.bookjeok.domains.book.query.dto.BookCategoryDTO;
import com.jamjam.bookjeok.domains.book.query.dto.BookDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface BookQueryAdminService {

    Page<BookDetailDTO> findBookListOrderByOption(Map<String, Object> params, PageRequest pageRequest);

    List<BookCategoryDTO> findBookCategory();

    BookDetailDTO findBook(Map<String, Object> params);
}
