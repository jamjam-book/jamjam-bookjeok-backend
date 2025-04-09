package com.jamjam.bookjeok.domains.order.repository.cart.mapper;

import com.jamjam.bookjeok.domains.book.entity.Book;
import com.jamjam.bookjeok.domains.member.entity.Member;
import com.jamjam.bookjeok.domains.order.entity.Cart;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface CartMapper {

    Optional<Member> findMemberById(Long memberUid);

    Optional<Book> findBookByIdAndBookNameAndPrice(Long bookId, String bookName, int price);

    int findCartCountByMemberUid(Long memberUid);

    Optional<Cart> findCartByMemberUidAndBookId(Long memberUid, Long bookId);

}