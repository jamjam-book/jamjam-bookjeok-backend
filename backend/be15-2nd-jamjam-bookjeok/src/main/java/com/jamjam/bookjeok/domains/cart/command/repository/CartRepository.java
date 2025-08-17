package com.jamjam.bookjeok.domains.cart.command.repository;

import com.jamjam.bookjeok.domains.cart.command.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findCartByMemberUidAndBookId(Long memberUid, Long bookId);

    Cart findByMemberUidAndBookId(Long memberUid, Long bookId);

    void deleteByMemberUidAndBookId(Long memberUid, Long bookId);

}