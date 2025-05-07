package com.jamjam.bookjeok.domains.cart.command.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.cart.command.dto.request.CartRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CartCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("장바구니에 도서를 등록하는 테스트")
    void testCreateBookToCart() throws Exception {
        String memberId = "user01";
        CartRequest cartRequest = CartRequest.builder()
                .bookId(33L)
                .bookName("문학으로 본 심리학")
                .quantity(10)
                .build();

        mockMvc.perform(post("/api/v1/members/" + memberId + "/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest))
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.memberUid").exists())
                .andExpect(jsonPath("$.data.memberUid").value(1L))
                .andExpect(jsonPath("$.data.bookId").exists())
                .andExpect(jsonPath("$.data.bookId").value(33L))
                .andExpect(jsonPath("$.data.bookName").value("문학으로 본 심리학"))
                .andExpect(jsonPath("$.data.totalPrice").value(215000))
                .andExpect(jsonPath("$.data.quantity").value(10))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("장바구니에 도서를 등록할 때 필요한 정보[책 이름]가 없으면 예외가 발생하는 테스트")
    void testCreateBookToCartException() throws Exception {
        String memberId = "user01";
        CartRequest cartRequest = CartRequest.builder()
                .bookId(2L)
                .quantity(10)
                .build();

        mockMvc.perform(post("/api/v1/members/" + memberId + "/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("책 이름은 비어있을 수 없습니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("장바구니에 있는 도서 수량을 변경하는 테스트")
    void testModifyBookQuantity() throws Exception {
        String memberId = "user01";
        CartRequest cartRequest = CartRequest.builder()
                .bookId(1L)
                .bookName("우리가 빛의 속도로 갈 수 없다면")
                .quantity(5)
                .build();

        mockMvc.perform(put("/api/v1/members/" + memberId + "/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.memberUid").exists())
                .andExpect(jsonPath("$.data.memberUid").value(1L))
                .andExpect(jsonPath("$.data.bookId").exists())
                .andExpect(jsonPath("$.data.bookId").value(1L))
                .andExpect(jsonPath("$.data.bookName").value("우리가 빛의 속도로 갈 수 없다면"))
                .andExpect(jsonPath("$.data.totalPrice").value(105000))
                .andExpect(jsonPath("$.data.quantity").value(5))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("장바구니에 도서 정보가 없을 때 수량 변경을 시도하면 예외가 발생하는 테스트")
    void testModifyBookQuantityException() throws Exception {
        String memberId = "user01";
        CartRequest cartRequest = CartRequest.builder()
                .bookId(3L)
                .bookName("노르웨이의 숲")
                .quantity(7)
                .build();

        mockMvc.perform(put("/api/v1/members/" + memberId + "/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("장바구니에 해당 도서 정보가 없습니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("장바구니에 있는 도서 정보를 삭제하는 테스트")
    void testDeleteBookFromCartByMemberId() throws Exception {
        String memberId = "user01";
        CartRequest cartRequest = CartRequest.builder()
                .bookId(1L)
                .bookName("우리가 빛의 속도로 갈 수 없다면")
                .quantity(10)
                .build();

        mockMvc.perform(delete("/api/v1/members/" + memberId + "/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("장바구니에 없는 도서 정보를 삭제할 때 예외가 발생하는 테스트")
    void testDeleteBookFromCartByMemberIdException() throws Exception {
        String memberId = "user01";;
        CartRequest cartRequest = CartRequest.builder()
                .bookId(100L)
                .bookName("Clean Code")
                .quantity(1)
                .build();

        mockMvc.perform(delete("/api/v1/members/" + memberId + "/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.message").value("장바구니에 해당 도서 정보가 없습니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

}