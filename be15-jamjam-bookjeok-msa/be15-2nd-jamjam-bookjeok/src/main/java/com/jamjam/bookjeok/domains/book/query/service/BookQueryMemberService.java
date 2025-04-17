package com.jamjam.bookjeok.domains.book.query.service;

import com.jamjam.bookjeok.domains.book.command.dto.request.ReviewRequest;
import com.jamjam.bookjeok.domains.book.query.dto.BookDetailDTO;
import com.jamjam.bookjeok.domains.book.query.dto.BookDetailPageDTO;
import com.jamjam.bookjeok.domains.book.query.dto.PopularBookDTO;

import java.util.List;
import java.util.Map;

public interface BookQueryMemberService {

    List<BookDetailDTO> getBookList(Map<String, Object> params);

    BookDetailPageDTO getBookDetail(Map<String, Object> params);

    List<PopularBookDTO> getPopularBooks();

    boolean validCheckBuyer(ReviewRequest reviewRequest);
}
