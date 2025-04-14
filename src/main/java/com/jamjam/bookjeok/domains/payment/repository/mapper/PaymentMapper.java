package com.jamjam.bookjeok.domains.payment.repository.mapper;

import com.jamjam.bookjeok.domains.book.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface PaymentMapper {

    Optional<Book> findBookByBookId(Long bookId);

}