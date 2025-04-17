package com.jamjam.bookjeok.domains.cart.query.service;

import com.jamjam.bookjeok.domains.cart.query.dto.response.CartBookListResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class CartQueryServiceImplTest {

    @Autowired
    private CartQueryService cartQueryService;

    @Test
    @DisplayName("memberUid로 장바구니 목록 조회")
    void testGetBooksInCart() {
        Long memberUid = 1L;

        CartBookListResponse cartBookListResponse = cartQueryService.getBooksInCart(memberUid);

        assertThat(cartBookListResponse).isNotNull();
    }

}