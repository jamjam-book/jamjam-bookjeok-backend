package com.jamjam.bookjeok.domains.order.service.order;

import com.jamjam.bookjeok.domains.order.entity.Order;
import com.jamjam.bookjeok.domains.order.entity.PendingOrder;
import com.jamjam.bookjeok.domains.payment.dto.PaymentDTO;

public interface OrderService {

    Order createOrder(PendingOrder findPendingOrder, PaymentDTO paymentDTO);

}