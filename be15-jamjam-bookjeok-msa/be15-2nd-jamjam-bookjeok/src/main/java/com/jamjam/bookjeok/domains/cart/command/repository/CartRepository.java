package com.jamjam.bookjeok.domains.cart.command.repository;

import com.jamjam.bookjeok.domains.cart.command.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}