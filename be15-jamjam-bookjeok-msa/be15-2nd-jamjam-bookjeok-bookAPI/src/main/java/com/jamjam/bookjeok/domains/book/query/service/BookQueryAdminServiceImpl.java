package com.jamjam.bookjeok.domains.book.query.service;

import com.jamjam.bookjeok.domains.book.query.dto.AuthorDTO;
import com.jamjam.bookjeok.domains.book.query.dto.BookCategoryDTO;
import com.jamjam.bookjeok.domains.book.query.dto.BookDetailDTO;
import com.jamjam.bookjeok.domains.book.query.mapper.BookMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookQueryAdminServiceImpl implements BookQueryAdminService {

    private final BookMapper bookMapper;

    @Override
    @Transactional
    public List<BookDetailDTO> findBookListOrderByOption(Map<String, Object> params) {

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
    public BookDetailDTO findBook(Map<String, String> params) {

        BookDetailDTO book = bookMapper.findBookByIsbn(params);

        log.info("{}", book.toString());

        return book;

    }

    @Override
    @Transactional
    public List<BookCategoryDTO> findBookCategory() {
        return bookMapper.findAllCategory();
    }


}
