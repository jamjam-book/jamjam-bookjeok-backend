package com.jamjam.bookjeok.domains.pendingorder.command.service;

import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderBookItemsRequest;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderRequest;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.response.PendingOrderResponse;
import com.jamjam.bookjeok.exception.cart.CartItemLimitExceededException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class PendingOrderCommandServiceImplTest {

    @Autowired
    private PendingOrderCommandService pendingOrderCommandService;

    private List<PendingOrderBookItemsRequest> pendingOrderBookItemsRequest;

    @BeforeEach
    void setUp() {
        pendingOrderBookItemsRequest = new ArrayList<>();
        pendingOrderBookItemsRequest.add(PendingOrderBookItemsRequest.builder()
                .bookId(1L)
                .bookName("우리가 빛의 속도로 갈 수 없다면")
                .quantity(10)
                .totalPrice(210000)
                .build()
        );
        pendingOrderBookItemsRequest.add(PendingOrderBookItemsRequest.builder()
                .bookId(12L)
                .bookName("7년의 밤")
                .quantity(3)
                .totalPrice(32700)
                .build()
        );
    }

    @Test
    @DisplayName("임시 주문 정보를 등록하는 테스트")
    void testCreateOrder() {
        PendingOrderRequest pendingOrderRequest = PendingOrderRequest.builder()
                .memberUid(1L)
                .orderBookItems(pendingOrderBookItemsRequest)
                .build();

        PendingOrderResponse savedPendingOrder = pendingOrderCommandService.createOrder(pendingOrderRequest);

        log.info("savedPendingOrder = {}", savedPendingOrder);

        assertThat(savedPendingOrder).isNotNull();
        assertThat(savedPendingOrder.orderId()).isNotEmpty();
        assertThat(savedPendingOrder.totalAmount()).isEqualTo(242700);
    }

    @Test
    @DisplayName("임시 주문 정보를 등록할 때 도서 정보가 없으면 예외가 발생하는 테스트")
    void testCreateOrderException() {
        pendingOrderBookItemsRequest.add(PendingOrderBookItemsRequest.builder()
                .bookId(10L)
                .bookName("Clean Code")
                .quantity(1)
                .totalPrice(25000)
                .build()
        );

        PendingOrderRequest pendingOrderRequest = PendingOrderRequest.builder()
                .memberUid(1L)
                .orderBookItems(pendingOrderBookItemsRequest)
                .build();

        assertThatThrownBy(() -> pendingOrderCommandService.createOrder(pendingOrderRequest))
                .isInstanceOf(CartItemLimitExceededException.class)
                .hasMessage("존재하지 않는 도서 정보 입니다.");
    }

}