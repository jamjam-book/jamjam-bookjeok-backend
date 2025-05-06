package com.jamjam.bookjeok.domains.order.command.controller;

import com.jamjam.bookjeok.domains.member.command.dto.response.MemberDetailResponse;
import com.jamjam.bookjeok.domains.member.query.dto.MemberDTO;
import com.jamjam.bookjeok.domains.member.query.service.MemberQueryServiceImpl;
import com.jamjam.bookjeok.domains.order.command.dto.OrderDTO;
import com.jamjam.bookjeok.domains.order.command.dto.OrderItemDTO;
import com.jamjam.bookjeok.domains.order.command.dto.response.OrderResponse;
import com.jamjam.bookjeok.domains.order.command.dto.response.PageOrderResponse;
import com.jamjam.bookjeok.domains.order.command.service.OrderCommandServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
@ImportAutoConfiguration(exclude = SecurityAutoConfiguration.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberQueryServiceImpl memberQueryService;

    @MockBean
    private OrderCommandServiceImpl orderCommandService;

    private List<OrderDTO> orders;

    @BeforeEach
    void setUp() {
        orders = List.of(
                OrderDTO.builder()
                        .orderId("ORD001")
                        .orderedAt(LocalDateTime.of(2025, 4, 9, 10, 0, 0))
                        .orderStatusName("결제승인")
                        .items(List.of(
                                OrderItemDTO.builder()
                                        .bookName("책1")
                                        .quantity(2)
                                        .totalPrice(20000)
                                        .imageUrl("http://localhost:8080/book1.png")
                                        .build()
                        ))
                        .build(),

                OrderDTO.builder()
                        .orderId("ORD002")
                        .orderedAt(LocalDateTime.of(2025, 4, 10, 10, 0, 0))
                        .orderStatusName("결제승인")
                        .items(List.of(
                                OrderItemDTO.builder()
                                        .bookName("책2")
                                        .quantity(1)
                                        .totalPrice(15000)
                                        .imageUrl("http://localhost:8080/book2.png")
                                        .build()
                        ))
                        .build()
        );
    }

    @Test
    @DisplayName("회원id로 주문 정보를 조회하는 테스트")
    void testGetOrders() throws Exception {
        // given
        String memberId = "test01";
        Long memberUid = 1L;

        PageOrderResponse pageOrderResponse = PageOrderResponse.builder()
                .orders(orders)
                .pageNumber(0)
                .pageSize(10)
                .totalElements(2)
                .totalPages(1)
                .build();

        MemberDetailResponse memberDetailResponse = MemberDetailResponse.builder()
                .member(MemberDTO.builder()
                        .memberUid(memberUid)
                        .build())
                .build();

        when(memberQueryService.getMemberDetail(memberId)).thenReturn(memberDetailResponse);
        when(orderCommandService.getOrdersByMemberUid(any(Pageable.class), eq(memberUid))).thenReturn(pageOrderResponse);

        // when & then
        mockMvc.perform(get("/api/v1/members/{memberId}/orders", memberId)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.orders").isArray())
                .andExpect(jsonPath("$.data.pageNumber").value(0))
                .andExpect(jsonPath("$.data.pageSize").value(10))
                .andExpect(jsonPath("$.data.totalElements").value(2))
                .andExpect(jsonPath("$.data.totalPages").value(1))
                .andExpect(jsonPath("$.timestamp").exists());
    }

}