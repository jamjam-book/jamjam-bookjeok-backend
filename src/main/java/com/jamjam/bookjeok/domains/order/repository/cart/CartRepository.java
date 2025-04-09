package com.jamjam.bookjeok.domains.order.repository.cart;

import com.jamjam.bookjeok.domains.order.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}