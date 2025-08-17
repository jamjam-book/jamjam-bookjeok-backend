package com.jamjam.bookjeok.domains.book.query.service;

import com.jamjam.bookjeok.domains.book.query.dto.AuthorDTO;
import com.jamjam.bookjeok.domains.book.query.dto.BookCategoryDTO;
import com.jamjam.bookjeok.domains.book.query.dto.BookDetailDTO;
import com.jamjam.bookjeok.domains.book.query.mapper.BookMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    @Value("${image.image-url}")
    private String fileUrl;

    @Override
    @Transactional
    public Page<BookDetailDTO> findBookListOrderByOption(Map<String, Object> params, PageRequest pageRequest) {

        int offset = (int) pageRequest.getOffset();
        int limit = pageRequest.getPageSize();

        params.put("offset", offset);
        params.put("limit", limit);

        List<BookDetailDTO> books = bookMapper.findBookListOrderByOptionMap(params);

        // 총 개수 조회
        int total = bookMapper.countBookListByOption(params);

        for (BookDetailDTO book : books) {
            List<AuthorDTO> authors = new ArrayList<>();
            if (book.getAuthorNames() != null) {
                authors = Arrays.stream(book.getAuthorNames().split(",\\s*"))
                        .map(AuthorDTO::new)
                        .toList();
            }
            book.setImage(fileUrl + book.getImageUrl());
            book.addList(authors);
        }

        return new PageImpl<>(books, pageRequest, total);
    }

    @Override
    @Transactional
    public BookDetailDTO findBook(Map<String, Object> params) {

        BookDetailDTO book = bookMapper.findBook(params);

        log.info("{}", book.toString());

        return book;

    }

    @Override
    @Transactional
    public List<BookCategoryDTO> findBookCategory() {
        return bookMapper.findAllCategory();
    }


}

