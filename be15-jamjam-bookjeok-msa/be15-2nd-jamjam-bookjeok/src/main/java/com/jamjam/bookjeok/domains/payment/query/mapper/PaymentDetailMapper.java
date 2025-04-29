package com.jamjam.bookjeok.domains.payment.query.mapper;

import com.jamjam.bookjeok.domains.payment.query.dto.PaymentDetailDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentDetailMapper {

    PaymentDetailDTO findPaymentDetailByPaymentId(Long paymentId, String paymentMethod);

}