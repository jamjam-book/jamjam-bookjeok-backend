package com.jamjam.bookjeok.domains.cart.command.service;

import com.jamjam.bookjeok.domains.cart.command.dto.request.CartRequest;
import com.jamjam.bookjeok.domains.cart.command.dto.response.CartResponse;
import com.jamjam.bookjeok.exception.cart.CartBookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class CartCommandServiceImplTest {

    @Autowired
    private CartCommandService cartCommandService;

    @Test
    @DisplayName("장바구니에 도서 정보 추가하는 테스트")
    void testCreateBookToCart() {
        CartRequest cartRequest = CartRequest.builder()
                .memberUid(1L)
                .bookId(33L)
                .bookName("문학으로 본 심리학")
                .quantity(10)
                .build();

        CartResponse cartResponse = cartCommandService.createBookToCart(cartRequest);

        log.info("cartResponse: {}", cartResponse.toString());

        assertThat(cartResponse).isNotNull();

        assertThat(cartResponse.memberUid()).isEqualTo(1L);
        assertThat(cartResponse.bookId()).isEqualTo(33L);
        assertThat(cartResponse.bookName()).isEqualTo("문학으로 본 심리학");
        assertThat(cartResponse.totalPrice()).isEqualTo(215000);
        assertThat(cartResponse.quantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("장바구니에 도서 정보 추가할 때 동일한 도서 정보가 있으면 수량을 증가시키는 테스트")
    void testCreateBookToCartAddQuantity() {
        CartRequest cartRequest = CartRequest.builder()
                .memberUid(1L)
                .bookId(1L)
                .bookName("우리가 빛의 속도로 갈 수 없다면")
                .quantity(5)
                .build();

        CartResponse cartResponse = cartCommandService.createBookToCart(cartRequest);

        log.info("cartResponse: {}", cartResponse.toString());

        assertThat(cartResponse).isNotNull();

        assertThat(cartResponse.memberUid()).isEqualTo(1L);
        assertThat(cartResponse.bookId()).isEqualTo(1L);
        assertThat(cartResponse.bookName()).isEqualTo("우리가 빛의 속도로 갈 수 없다면");
        assertThat(cartResponse.totalPrice()).isEqualTo(315000);
        assertThat(cartResponse.quantity()).isEqualTo(15);
    }

    @Test
    @DisplayName("장바구니에 도서 정보가 존재하는 경우 도서 수량을 변경하는 테스트")
    void testModifyBookQuantity() {
        CartRequest cartRequest = CartRequest.builder()
                .memberUid(1L)
                .bookId(1L)
                .bookName("우리가 빛의 속도로 갈 수 없다면")
                .quantity(3)
                .build();

        CartResponse cartResponse = cartCommandService.modifyBookQuantity(cartRequest);

        log.info("cartResponse: {}", cartResponse.toString());

        assertThat(cartResponse).isNotNull();
        assertThat(cartResponse.memberUid()).isEqualTo(1L);
        assertThat(cartResponse.bookId()).isEqualTo(1L);
        assertThat(cartResponse.bookName()).isEqualTo("우리가 빛의 속도로 갈 수 없다면");
        assertThat(cartResponse.totalPrice()).isEqualTo(63000);
        assertThat(cartResponse.quantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("장바구니에서 도서 수량을 변경할 때, 도서 정보가 존재하지 않으면 예외가 발생하는 테스트")
    void testModifyBookQuantityException() {
        CartRequest cartRequest = CartRequest.builder()
                .memberUid(1L)
                .bookId(32L)
                .bookName("다 함께 쓰는 여행기")
                .quantity(3)
                .build();

        assertThatThrownBy(() -> cartCommandService.modifyBookQuantity(cartRequest))
                .isInstanceOf(CartBookNotFoundException.class)
                .hasMessage("장바구니에 해당 도서 정보가 없습니다.");
    }

    @Test
    @DisplayName("memberUid와 bookId로 장바구니에 있는 도서 정보 삭제하는 테스트")
    void testDeleteBookFromCartByMemberId() {
        CartRequest cartRequest = CartRequest.builder()
                .memberUid(1L)
                .bookId(1L)
                .bookName("우리가 빛의 속도로 갈 수 없다면")
                .quantity(10)
                .build();

        assertDoesNotThrow(() -> cartCommandService.deleteBookFromCartByMemberId(cartRequest));
    }

    @Test
    @DisplayName("장바구니에 없는 도서 정보를 삭제할 때 예외가 발생하는 테스트")
    void testDeleteBookFromCartByMemberIdException() {
        CartRequest cartRequest = CartRequest.builder()
                .memberUid(1L)
                .bookId(2L)
                .bookName("채식주의자")
                .quantity(1)
                .build();

        assertThatThrownBy(() -> cartCommandService.deleteBookFromCartByMemberId(cartRequest))
                .isInstanceOf(CartBookNotFoundException.class)
                .hasMessage("장바구니에 해당 도서 정보가 없습니다.");
    }

}