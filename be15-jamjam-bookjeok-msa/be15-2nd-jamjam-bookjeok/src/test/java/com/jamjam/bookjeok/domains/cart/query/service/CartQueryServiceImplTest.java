package com.jamjam.bookjeok.domains.cart.query.service;

import com.jamjam.bookjeok.domains.cart.query.dto.response.CartBookListResponse;
import com.jamjam.bookjeok.domains.cart.query.dto.response.CartBookResponse;
import com.jamjam.bookjeok.domains.cart.query.dto.response.CartCountResponse;
import com.jamjam.bookjeok.domains.cart.query.mapper.CartMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartQueryServiceImplTest {

    @InjectMocks
    private CartQueryServiceImpl cartQueryService;

    @Mock
    private CartMapper cartMapper;

    private List<CartBookResponse> bookList;

    @BeforeEach
    void setUp() {
        bookList = List.of(
                CartBookResponse.builder()
                        .bookId(1L)
                        .bookName("우리가 빛의 속도로 갈 수 없다면")
                        .quantity(5)
                        .totalPrice(50000)
                        .imageUrl("localhost:8080/book1.png")
                        .build()
        );
    }

    @Test
    @DisplayName("memberUid로 장바구니 목록 조회")
    void testGetBooksInCart() {
        Long memberUid = 1L;

        when(cartMapper.findCartBooksByMemberUid(memberUid)).thenReturn(bookList);

        CartBookListResponse cartBookListResponse = cartQueryService.getBooksInCart(memberUid);

        assertThat(cartBookListResponse).isNotNull();
        assertThat(cartBookListResponse.bookList().get(0).bookId()).isEqualTo(1L);
        assertThat(cartBookListResponse.bookList().get(0).bookName()).isEqualTo("우리가 빛의 속도로 갈 수 없다면");
        assertThat(cartBookListResponse.bookList().get(0).quantity()).isEqualTo(5);
        assertThat(cartBookListResponse.bookList().get(0).totalPrice()).isEqualTo(50000);
        assertThat(cartBookListResponse.bookList().get(0).imageUrl()).isEqualTo("localhost:8080/book1.png");
    }

    @Test
    @DisplayName("memberUid로 회원 장바구니 개수 조회")
    void testGetCartCount() {
        Long memberUid = 1L;

        when(cartMapper.findCartCountByMemberUid(memberUid)).thenReturn(5);

        CartCountResponse cartCountResponse = cartQueryService.getCartCount(memberUid);

        assertThat(cartCountResponse).isNotNull();
        assertThat(cartCountResponse.cartCount()).isEqualTo(5);
    }

}