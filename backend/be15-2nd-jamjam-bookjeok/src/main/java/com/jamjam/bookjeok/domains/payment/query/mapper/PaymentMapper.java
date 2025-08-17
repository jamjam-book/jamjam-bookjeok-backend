package com.jamjam.bookjeok.domains.payment.query.mapper;

import com.jamjam.bookjeok.domains.payment.command.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface PaymentMapper {

    Optional<Payment> findPaymentByPaymentId(Long paymentId);

}