package com.jamjam.bookjeok.domains.order.command.repository;

import com.jamjam.bookjeok.domains.order.command.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}