package com.jamjam.bookjeok.domains.order.service.cart;

import com.jamjam.bookjeok.domains.order.dto.cart.response.CartResponse;
import com.jamjam.bookjeok.domains.order.dto.cart.request.CartRequest;
import com.jamjam.bookjeok.exception.order.cart.CartBookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class CartServiceImplTest {

    @Autowired
    private CartService cartService;

    @Test
    @DisplayName("장바구니에 도서 정보 추가하는 테스트")
    void testCreateBookToCart() {
        CartRequest cartRequest = CartRequest.builder()
                .memberUid(1L)
                .bookId(2L)
                .bookName("채식주의자")
                .price(13000)
                .quantity(10)
                .build();

        CartResponse cartResponse = cartService.createBookToCart(cartRequest);

        log.info("cartResponse: {}", cartResponse.toString());

        assertThat(cartResponse).isNotNull();

        assertThat(cartResponse.memberUid()).isEqualTo(1L);
        assertThat(cartResponse.bookId()).isEqualTo(2L);
        assertThat(cartResponse.bookName()).isEqualTo("채식주의자");
        assertThat(cartResponse.totalPrice()).isEqualTo(130000);
        assertThat(cartResponse.quantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("장바구니에 도서 정보 추가할 때 동일한 책 정보가 있으면 수량을 증가시키는 테스트")
    void testCreateBookToCartAddQuantity() {
        CartRequest cartRequest = CartRequest.builder()
                .memberUid(1L)
                .bookId(1L)
                .bookName("태백산맥 1권")
                .price(18000)
                .quantity(10)
                .build();

        CartResponse cartResponse = cartService.createBookToCart(cartRequest);

        log.info("cartResponse: {}", cartResponse.toString());

        assertThat(cartResponse).isNotNull();

        assertThat(cartResponse.memberUid()).isEqualTo(1L);
        assertThat(cartResponse.bookId()).isEqualTo(1L);
        assertThat(cartResponse.bookName()).isEqualTo("태백산맥 1권");
        assertThat(cartResponse.totalPrice()).isEqualTo(360000);
        assertThat(cartResponse.quantity()).isEqualTo(20);
    }

    @Test
    @DisplayName("장바구니에 도서 정보가 존재하는 경우 도서 수량을 변경하는 테스트")
    void testModifyBookQuantity() {
        CartRequest cartRequest = CartRequest.builder()
                .memberUid(1L)
                .bookId(1L)
                .bookName("태백산맥 1권")
                .price(18000)
                .quantity(5)
                .build();

        CartResponse cartResponse = cartService.modifyBookQuantity(cartRequest);

        log.info("cartResponse: {}", cartResponse.toString());

        assertThat(cartResponse).isNotNull();
        assertThat(cartResponse.memberUid()).isEqualTo(1L);
        assertThat(cartResponse.bookId()).isEqualTo(1L);
        assertThat(cartResponse.totalPrice()).isEqualTo(90000);
        assertThat(cartResponse.quantity()).isEqualTo(5);
    }

    @Test
    @DisplayName("장바구니에서 도서 수량을 변경할 때, 도서 정보가 존재하지 않으면 예외가 발생하는 테스트")
    void testModifyBookQuantityException() {
        CartRequest cartRequest = CartRequest.builder()
                .memberUid(1L)
                .bookId(2L)
                .bookName("채식주의자")
                .price(13000)
                .quantity(3)
                .build();

        assertThatThrownBy(() -> cartService.modifyBookQuantity(cartRequest))
                .isInstanceOf(CartBookNotFoundException.class)
                .hasMessage("장바구니에 추가된 도서만 수량을 변경할 수 있습니다.");
    }

}