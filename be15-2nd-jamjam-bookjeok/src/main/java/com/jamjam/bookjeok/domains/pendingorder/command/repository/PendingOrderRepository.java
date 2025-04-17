package com.jamjam.bookjeok.domains.pendingorder.command.repository;

import com.jamjam.bookjeok.domains.pendingorder.command.entity.PendingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PendingOrderRepository extends JpaRepository<PendingOrder, Long> {

    Optional<PendingOrder> findPendingOrderByOrderIdAndTotalAmount(String orderId, int totalAmount);

}