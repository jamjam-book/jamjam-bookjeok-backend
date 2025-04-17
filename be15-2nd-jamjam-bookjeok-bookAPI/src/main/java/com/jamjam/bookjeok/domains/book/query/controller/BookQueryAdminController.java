package com.jamjam.bookjeok.domains.book.query.controller;

import com.jamjam.bookjeok.domains.book.query.dto.BookCategoryDTO;
import com.jamjam.bookjeok.domains.book.query.dto.BookDetailDTO;
import com.jamjam.bookjeok.domains.book.query.service.BookQueryAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class BookQueryAdminController {

    private final BookQueryAdminService bookQueryAdminService;

    @GetMapping("/book/list")
    public List<BookDetailDTO> selectBook(@RequestParam Map<String, Object> params) {

        return bookQueryAdminService.findBookListOrderByOption(params);
    }

    @GetMapping("/book/{isbn}")
    public BookDetailDTO selectBookDetail(@PathVariable String isbn) {

        Map<String, String> params = new HashMap<>();
        params.put("isbn", isbn);

        return bookQueryAdminService.findBook(params);
    }

    @GetMapping("book/ca/list")
    public List<BookCategoryDTO> selectCategory() {
        return bookQueryAdminService.findBookCategory();
    }

}
