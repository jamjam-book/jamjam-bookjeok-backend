package com.jamjam.bookjeok.domains.order.command.service;


import com.jamjam.bookjeok.domains.order.command.entity.Order;
import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.pendingorder.command.entity.PendingOrder;

public interface OrderCommandService {

    Order createOrder(PendingOrder findPendingOrder, PaymentDTO paymentDTO);

}