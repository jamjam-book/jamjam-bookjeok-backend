package com.jamjam.bookjeok.domains.order.entity;

import com.jamjam.bookjeok.exception.order.cart.CartItemLimitExceededException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static com.jamjam.bookjeok.domains.order.entity.Cart.calculateBookTotalPrice;
import static com.jamjam.bookjeok.domains.order.entity.Cart.validateCartItemLimit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@ActiveProfiles("test")
class CartTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    @DisplayName("장바구니에 도서 정보가 20개 이하인 경우 통과하는 테스트")
    void testValidateCartItemLimit(int condition) {
        assertThatCode(() -> validateCartItemLimit(condition))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("장바구니에 20개가 넘는 도서 정보가 있으면 예외가 발생하는 테스트")
    void testValidateCartItemLimitException() {
        int cartCount = 20;

        Assertions.assertThatThrownBy(() -> Cart.validateCartItemLimit(cartCount))
                .isInstanceOf(CartItemLimitExceededException.class)
                .hasMessage("장바구니에는 최대 20까지 도서를 담을 수 있습니다.");
    }

    @Test
    @DisplayName("도서 수량, 가격으로 금액을 구하는 테스트")
    void testCalculateBookTotalPrice() {
        int quantity = 10;
        int price = 35000;
        int expectedValue = 350000;

        int calculatedBookTotalPrice = calculateBookTotalPrice(quantity, price);

        assertThat(calculatedBookTotalPrice).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("장바구니에서 도서 수량을 추가하는 테스트")
    void testAddQuantity() {
        Cart cart = Cart.builder()
                .memberUid(1L)
                .bookId(1L)
                .quantity(10)
                .createdAt(LocalDateTime.now().withNano(0))
                .build();
        cart.addQuantity(20);

        assertThat(cart.getQuantity()).isEqualTo(30);
    }

}