package com.jamjam.bookjeok.domains.orderdetail.command.repository;

import com.jamjam.bookjeok.domains.orderdetail.command.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findOrderDetailByOrderUid(Long orderUid);
}