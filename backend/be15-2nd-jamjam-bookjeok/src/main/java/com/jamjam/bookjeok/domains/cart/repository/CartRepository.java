package com.jamjam.bookjeok.domains.cart.repository;

import com.jamjam.bookjeok.domains.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}