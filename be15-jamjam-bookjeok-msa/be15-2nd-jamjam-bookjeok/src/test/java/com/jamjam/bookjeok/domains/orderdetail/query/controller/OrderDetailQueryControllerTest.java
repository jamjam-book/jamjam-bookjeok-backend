package com.jamjam.bookjeok.domains.orderdetail.query.controller;

import com.jamjam.bookjeok.domains.member.command.dto.response.MemberDetailResponse;
import com.jamjam.bookjeok.domains.member.query.dto.MemberDTO;
import com.jamjam.bookjeok.domains.member.query.service.MemberQueryServiceImpl;
import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailDTO;
import com.jamjam.bookjeok.domains.orderdetail.query.service.OrderDetailQueryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderDetailQueryController.class)
@ImportAutoConfiguration(exclude = SecurityAutoConfiguration.class)
class OrderDetailQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderDetailQueryServiceImpl orderDetailQueryService;

    @MockBean
    private MemberQueryServiceImpl memberQueryService;

    private List<OrderDetailDTO> orderDetails;

    @BeforeEach
    void setUp() {
        orderDetails = List.of(
                OrderDetailDTO.builder()
                        .orderId("ORD001")
                        .bookName("책이름01")
                        .isbn("9781082502299")
                        .totalPrice(15000)
                        .quantity(1)
                        .imageUrl("http://localhost:8080/productimgs/604248.png")
                        .orderedAt(LocalDateTime.now().withNano(0))
                        .build()
        );
    }

    @Test
    @DisplayName("회원id와 주문id로 주문 상세 내역을 조회하는 테스트")
    void testGetOrderDetail() throws Exception {
        String memberId = "test01";
        String orderId = "ORD001";
        Long memberUid = 1L;

        MemberDetailResponse memberDetailResponse = MemberDetailResponse.builder()
                .member(new MemberDTO(memberUid, null, null, null,
                        null, null, null, true,
                        null, null, null, null))
                .build();

        when(memberQueryService.getMemberDetail(memberId)).thenReturn(memberDetailResponse);
        when(orderDetailQueryService.getOrderDetailByMemberUidAndOrderId(memberUid, orderId)).thenReturn(orderDetails);

        mockMvc.perform(get("/api/v1/members/" + memberId + "/orders/" + orderId + "/order-detail")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.orderDetails").exists())
                .andExpect(jsonPath("$.data.orderDetails").isArray())
                .andExpect(jsonPath("$.data.orderDetails[0].orderId").value("ORD001"))
                .andExpect(jsonPath("$.data.orderDetails[0].bookName").value("책이름01"))
                .andExpect(jsonPath("$.data.orderDetails[0].isbn").value("9781082502299"))
                .andExpect(jsonPath("$.data.orderDetails[0].totalPrice").value(15000))
                .andExpect(jsonPath("$.data.orderDetails[0].quantity").value(1))
                .andExpect(jsonPath("$.data.orderDetails[0].imageUrl").value("http://localhost:8080/productimgs/604248.png"))
                .andExpect(jsonPath("$.data.orderDetails[0].orderedAt").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

}