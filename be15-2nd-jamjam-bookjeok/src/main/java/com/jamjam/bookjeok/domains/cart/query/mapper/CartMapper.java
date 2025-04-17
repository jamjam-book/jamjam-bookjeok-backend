package com.jamjam.bookjeok.domains.cart.query.mapper;

import com.jamjam.bookjeok.domains.book.command.entity.Book;
import com.jamjam.bookjeok.domains.cart.command.entity.Cart;
import com.jamjam.bookjeok.domains.cart.query.dto.response.CartBookResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CartMapper {

    Optional<Book> findByBookIdAndBookName(Long bookId, String bookName);

    int findCartCountByMemberUid(Long memberUid);

    Optional<Cart> findCartByMemberUidAndBookId(Long memberUid, Long bookId);

    List<CartBookResponse> findCartBooksByMemberUid(Long memberUid);

}