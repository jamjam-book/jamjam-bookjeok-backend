package com.jamjam.bookjeok.domains.payment.command.service;

import com.jamjam.bookjeok.domains.order.command.entity.Order;
import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;

public interface PaymentEntityCommandService {

    void createPayment(PaymentDTO paymentDTO, Order order);

}