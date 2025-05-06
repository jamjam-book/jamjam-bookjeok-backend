package com.jamjam.bookjeok.domains.orderdetail.query.dto.response;

import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailBookDTO;
import com.jamjam.bookjeok.domains.payment.query.dto.PaymentDetailDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDetailResponse(
        String orderId, LocalDateTime orderedAt,
        List<OrderDetailBookDTO> books, PaymentDetailDTO paymentDetail
) {
}