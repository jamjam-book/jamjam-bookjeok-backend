package com.jamjam.bookjeok.domains.order.repository.cart.mapper;

import com.jamjam.bookjeok.domains.book.entity.Book;
import com.jamjam.bookjeok.domains.order.dto.cart.response.CartBookResponse;
import com.jamjam.bookjeok.domains.order.entity.Cart;
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