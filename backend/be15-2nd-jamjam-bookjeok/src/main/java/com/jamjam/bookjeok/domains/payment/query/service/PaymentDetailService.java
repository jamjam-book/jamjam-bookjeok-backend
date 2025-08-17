package com.jamjam.bookjeok.domains.payment.query.service;

import com.jamjam.bookjeok.domains.payment.query.dto.PaymentDetailDTO;

public interface PaymentDetailService {

    PaymentDetailDTO getPaymentDetail(Long paymentId);

    Long getPaymentId(String orderId);

}