package com.jamjam.bookjeok.domains.order.repository.order.pendingorder;

import com.jamjam.bookjeok.domains.order.entity.PendingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PendingOrderRepository extends JpaRepository<PendingOrder, Long> {

    Optional<PendingOrder> findPendingOrderByOrderIdAndTotalAmount(String orderId, int totalAmount);

    void deletePendingOrderByOrderId(String orderId);

}