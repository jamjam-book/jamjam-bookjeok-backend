package com.jamjam.bookjeok.domains.payment.repository.mapper;

import com.jamjam.bookjeok.domains.payment.dto.BookInventoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface PaymentMapper {

    Optional<BookInventoryDTO> findBookByBookId(Long bookId);

}