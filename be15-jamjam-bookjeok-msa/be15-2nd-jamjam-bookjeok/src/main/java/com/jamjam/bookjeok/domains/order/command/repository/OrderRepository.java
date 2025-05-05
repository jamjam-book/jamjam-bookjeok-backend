package com.jamjam.bookjeok.domains.order.command.repository;

import com.jamjam.bookjeok.domains.order.command.dto.OrderResponse;
import com.jamjam.bookjeok.domains.order.command.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
        SELECT new com.jamjam.bookjeok.domains.order.command.dto.OrderResponse(
            o.orderUid, o.orderId, o.orderName, o.totalAmount, os.orderStatusName, 
            o.orderedAt, o.canceledAt, o.modifiedAt, o.refundedAt, b.imageUrl, od.quantity
        )
        FROM Order o
        JOIN OrderStatus os ON o.orderStatusId = os.orderStatusId
        JOIN OrderDetail od ON od.orderUid = o.orderUid
        JOIN Book b ON b.bookId = od.bookId
        WHERE o.memberUid = :memberUid
    """)
    Page<OrderResponse> findAllOrdersByMemberUid(Pageable pageable, @Param("memberUid") Long memberUid);

}