package com.jamjam.bookjeok.domains.orderdetail.repository;

import com.jamjam.bookjeok.domains.orderdetail.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findOrderDetailByOrderUid(Long orderUid);

}