package com.jamjam.bookjeok.domains.order.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.command.dto.response.MemberDetailResponse;
import com.jamjam.bookjeok.domains.member.query.service.MemberQueryService;
import com.jamjam.bookjeok.domains.order.command.dto.response.PageOrderResponse;
import com.jamjam.bookjeok.domains.order.command.service.OrderCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final MemberQueryService memberQueryService;
    private final OrderCommandService orderCommandService;

    @GetMapping("/members/{memberId}/orders")
    public ResponseEntity<ApiResponse<PageOrderResponse>> getOrders(
            @PathVariable(value = "memberId") String memberId,
            @PageableDefault(sort = "orderedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        MemberDetailResponse memberDetail = memberQueryService.getMemberDetail(memberId);
        Long memberUid = memberDetail.getMember().getMemberUid();

        PageOrderResponse pageOrderResponseWrapper = orderCommandService.getOrdersByMemberUid(pageable, memberUid);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(pageOrderResponseWrapper));
    }

}