package com.jamjam.bookjeok.domains.cart.query.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
class CartQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("memberUid로 장바구니 목록을 조회하는 테스트")
    void testGetBooksInCart() throws Exception {
        Long memberUid = 1L;

        mockMvc.perform(get("/api/v1/carts/" + memberUid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.bookList[0].memberUid").exists())
                .andExpect(jsonPath("$.data.bookList[0].memberUid").value(1L))
                .andExpect(jsonPath("$.data.bookList[0].bookId").exists())
                .andExpect(jsonPath("$.data.bookList[0].bookId").value(1L))
                .andExpect(jsonPath("$.data.bookList[0].bookName").value("우리가 빛의 속도로 갈 수 없다면"))
                .andExpect(jsonPath("$.data.bookList[0].totalPrice").exists())
                .andExpect(jsonPath("$.data.bookList[0].totalPrice").value(210000))
                .andExpect(jsonPath("$.data.bookList[0].quantity").value(10))
                .andExpect(jsonPath("$.data.bookList[1].memberUid").exists())
                .andExpect(jsonPath("$.data.bookList[1].memberUid").value(1L))
                .andExpect(jsonPath("$.data.bookList[1].bookId").exists())
                .andExpect(jsonPath("$.data.bookList[1].bookId").value(5L))
                .andExpect(jsonPath("$.data.bookList[1].bookName").value("노르웨이의 숲"))
                .andExpect(jsonPath("$.data.bookList[1].totalPrice").exists())
                .andExpect(jsonPath("$.data.bookList[1].totalPrice").value(45000))
                .andExpect(jsonPath("$.data.bookList[1].quantity").value(3))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

}