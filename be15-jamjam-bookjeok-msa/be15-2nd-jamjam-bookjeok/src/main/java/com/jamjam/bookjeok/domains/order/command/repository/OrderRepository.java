package com.jamjam.bookjeok.domains.order.command.repository;

import com.jamjam.bookjeok.domains.order.command.dto.response.OrderResponse;
import com.jamjam.bookjeok.domains.order.command.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
                SELECT DISTINCT o.orderId
                FROM Order o
                WHERE o.memberUid = :memberUid
                ORDER BY o.orderedAt DESC
            """)
    Page<String> findOrderIdsByMemberUid(Pageable pageable, @Param("memberUid") Long memberUid);

    @Query("""
                SELECT new com.jamjam.bookjeok.domains.order.command.dto.response.OrderResponse(
                    o.orderUid, o.memberUid, o.orderId, b.bookName, od.totalPrice, os.orderStatusName, 
                    o.orderedAt, o.canceledAt, o.modifiedAt, o.refundedAt, b.imageUrl, od.quantity
                )
                FROM Order o
                JOIN OrderStatus os ON o.orderStatusId = os.orderStatusId
                JOIN OrderDetail od ON od.orderUid = o.orderUid
                JOIN Book b ON b.bookId = od.bookId
                WHERE o.orderId IN :orderIds
            """)
    List<OrderResponse> findOrderResponsesByOrderIds(@Param("orderIds") List<String> orderIds);

}