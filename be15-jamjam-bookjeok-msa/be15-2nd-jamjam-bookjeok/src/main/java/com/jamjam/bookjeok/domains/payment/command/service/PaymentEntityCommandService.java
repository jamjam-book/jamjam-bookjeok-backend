package com.jamjam.bookjeok.domains.payment.command.service;

import com.jamjam.bookjeok.domains.order.command.entity.Order;
import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.payment.command.entity.Payment;

public interface PaymentEntityCommandService {

    Payment createPayment(PaymentDTO paymentDTO, Order order);

}