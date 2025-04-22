package com.jamjam.bookjeok.domains.orderdetail.query.dto.response;

import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderDetailResponse(
    List<OrderDetailDTO> orderDetails
) {
}