package com.jamjam.bookjeok.domains.order.repository.order.pendingorder;

import com.jamjam.bookjeok.domains.order.entity.PendingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingOrderRepository extends JpaRepository<PendingOrder, Long> {

}