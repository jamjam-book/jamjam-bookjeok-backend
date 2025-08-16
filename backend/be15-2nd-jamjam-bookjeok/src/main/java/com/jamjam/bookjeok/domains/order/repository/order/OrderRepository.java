package com.jamjam.bookjeok.domains.order.repository.order;

import com.jamjam.bookjeok.domains.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}