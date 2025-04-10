package com.jamjam.bookjeok.domains.order.repository.cart.mapper;

import com.jamjam.bookjeok.domains.book.entity.Book;
import com.jamjam.bookjeok.domains.member.entity.Member;
import com.jamjam.bookjeok.domains.order.dto.cart.response.CartBookResponse;
import com.jamjam.bookjeok.domains.order.entity.Cart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class CartMapperTest {

    @Autowired
    private CartMapper cartMapper;

    @Test
    @DisplayName("memberUid로 장바구니 목록 조회")
    void testFindCartBooksByMemberUid() {
        Long memberUid = 1L;

        List<CartBookResponse> cartBookResponse = cartMapper.findCartBooksByMemberUid(memberUid);

        assertThat(cartBookResponse).isNotNull();
        assertThat(cartBookResponse.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("memberUid로 회원 정보를 찾는 테스트")
    void testFindMemberById() {
        Long memberUid = 1L;

        Optional<Member> findMember = cartMapper.findMemberById(memberUid);

        assertThat(findMember).isNotNull();
        assertThat(findMember.get().getMemberUid()).isEqualTo(memberUid);
    }

    @Test
    @DisplayName("bookId로 책 정보 찾는 테스트")
    void testFindByBookIdAndBookName() {
        Long bookId = 1L;
        String bookName = "태백산맥 1권";

        Optional<Book> findBook = cartMapper.findByBookIdAndBookName(bookId, bookName);

        assertThat(findBook).isNotNull();
        assertThat(findBook.get().getBookId()).isEqualTo(bookId);
    }

    @Test
    @DisplayName("장바구니에서 도서 정보 개수를 조회하는 테스트")
    void testFindCartCountByMemberUid() {
        Long memberUid = 1L;

        int count = cartMapper.findCartCountByMemberUid(memberUid);

        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니에서 memberUid와 bookId로 장바구니 정보 조회 ")
    void testFindCartByMemberUidAndBookId() {
        Cart cart = cartMapper.findCartByMemberUidAndBookId(1L, 1L).get();

        assertThat(cart).isNotNull();
        assertThat(cart.getMemberUid()).isEqualTo(1L);
        assertThat(cart.getBookId()).isEqualTo(1L);
    }

}