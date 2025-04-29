package com.jamjam.bookjeok.domains.payment.command.repository;

import com.jamjam.bookjeok.domains.payment.command.entity.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {

}