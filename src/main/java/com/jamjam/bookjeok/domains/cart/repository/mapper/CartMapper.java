package com.jamjam.bookjeok.domains.cart.repository.mapper;

import com.jamjam.bookjeok.domains.book.entity.Book;
import com.jamjam.bookjeok.domains.cart.dto.response.CartBookResponse;
import com.jamjam.bookjeok.domains.cart.entity.Cart;
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