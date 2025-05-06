package com.jamjam.bookjeok.domains.order.command.service;

import com.jamjam.bookjeok.domains.order.command.dto.response.PageOrderResponse;
import com.jamjam.bookjeok.domains.order.command.entity.Order;
import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.pendingorder.command.entity.PendingOrder;
import org.springframework.data.domain.Pageable;

public interface OrderCommandService {

    PageOrderResponse getOrdersByMemberUid(Pageable pageable, Long memberUid);

    Order createOrder(PendingOrder findPendingOrder, PaymentDTO paymentDTO);

}