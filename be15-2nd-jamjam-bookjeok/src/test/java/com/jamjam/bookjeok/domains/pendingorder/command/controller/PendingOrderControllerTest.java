package com.jamjam.bookjeok.domains.pendingorder.command.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderBookItemsRequest;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PendingOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    void testCreateOrder() throws Exception {
        PendingOrderRequest pendingOrderRequest = PendingOrderRequest.builder()
                .memberUid(1L)
                .orderBookItems(pendingOrderBookItemsRequest)
                .build();

        mockMvc.perform(post("/api/v1/order/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pendingOrderRequest))
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.orderId").exists())
                .andExpect(jsonPath("$.data.totalAmount").exists())
                .andExpect(jsonPath("$.data.totalAmount").value(242700))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.errorCode").doesNotExist())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("임시 주문 정보를 등록할 때 도서 정보가 없으면 예외가 발생하는 테스트")
    void testCreateOrderException() throws Exception {
        pendingOrderBookItemsRequest.add(PendingOrderBookItemsRequest.builder()
                .bookId(10L)
                .bookName("Clean Code")
                .quantity(1)
                .totalPrice(25000)
                .build());

        PendingOrderRequest pendingOrderRequest = PendingOrderRequest.builder()
                .memberUid(1L)
                .orderBookItems(pendingOrderBookItemsRequest)
                .build();

        mockMvc.perform(post("/api/v1/order/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pendingOrderRequest))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.errorCode").exists())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("존재하지 않는 도서 정보 입니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("임시 주문 정보를 등록할 때 bookId가 없으면 예외가 발생하는 테스트")
    void testCreateOrderNotBookIdException() throws Exception {
        pendingOrderBookItemsRequest.add(PendingOrderBookItemsRequest.builder()
                .bookName("Clean Code")
                .quantity(1)
                .totalPrice(25000)
                .build());

        PendingOrderRequest pendingOrderRequest = PendingOrderRequest.builder()
                .memberUid(1L)
                .orderBookItems(pendingOrderBookItemsRequest)
                .build();

        mockMvc.perform(post("/api/v1/order/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pendingOrderRequest))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.errorCode").exists())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("bookId는 필수 입니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("임시 주문 정보를 등록할 때 도서명이 없으면 예외가 발생하는 테스트")
    void testCreateOrderNotBookNameException() throws Exception {
        pendingOrderBookItemsRequest.add(PendingOrderBookItemsRequest.builder()
                .bookId(10L)
                .quantity(1)
                .totalPrice(25000)
                .build());

        PendingOrderRequest pendingOrderRequest = PendingOrderRequest.builder()
                .memberUid(1L)
                .orderBookItems(pendingOrderBookItemsRequest)
                .build();

        mockMvc.perform(post("/api/v1/order/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pendingOrderRequest))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.errorCode").exists())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("도서명은 필수 입니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("임시 주문 정보를 등록할 때 개수가 없으면 예외가 발생하는 테스트")
    void testCreateOrderNotBookQuantityException() throws Exception {
        pendingOrderBookItemsRequest.add(PendingOrderBookItemsRequest.builder()
                .bookId(10L)
                .bookName("Clean Code")
                .totalPrice(25000)
                .build());

        PendingOrderRequest pendingOrderRequest = PendingOrderRequest.builder()
                .memberUid(1L)
                .orderBookItems(pendingOrderBookItemsRequest)
                .build();

        mockMvc.perform(post("/api/v1/order/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pendingOrderRequest))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.errorCode").exists())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("수량은 1개 이상이어야 합니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("임시 주문 정보를 등록할 때 총 가격이 없으면 예외가 발생하는 테스트")
    void testCreateOrderNotBookTotalPriceException() throws Exception {
        pendingOrderBookItemsRequest.add(PendingOrderBookItemsRequest.builder()
                .bookId(10L)
                .bookName("Clean Code")
                .quantity(1)
                .build());

        PendingOrderRequest pendingOrderRequest = PendingOrderRequest.builder()
                .memberUid(1L)
                .orderBookItems(pendingOrderBookItemsRequest)
                .build();

        mockMvc.perform(post("/api/v1/order/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pendingOrderRequest))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.errorCode").exists())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("가격은 0 이하일 수 없습니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

}