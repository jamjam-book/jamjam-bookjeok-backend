package com.jamjam.bookjeok.domains.orderdetail.query.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.command.dto.response.MemberDetailResponse;
import com.jamjam.bookjeok.domains.member.query.service.MemberQueryService;
import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailDTO;
import com.jamjam.bookjeok.domains.orderdetail.query.dto.response.OrderDetailResponse;
import com.jamjam.bookjeok.domains.orderdetail.query.service.OrderDetailQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderDetailQueryController {

    private final MemberQueryService memberQueryService;
    private final OrderDetailQueryService orderDetailQueryService;

    @GetMapping("/members/{memberId}/orders/{orderId}/order-detail")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> getOrderDetail(
            @PathVariable(value = "memberId") String memberId,
            @PathVariable(value = "orderId") String orderId
    ) {
        MemberDetailResponse memberDetail = memberQueryService.getMemberDetail(memberId);
        Long memberUid = memberDetail.getMember().getMemberUid();

        List<OrderDetailDTO> orderDetails = orderDetailQueryService.getOrderDetailByMemberUidAndOrderId(memberUid, orderId);

        OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                .orderDetails(orderDetails)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(orderDetailResponse));
    }

}