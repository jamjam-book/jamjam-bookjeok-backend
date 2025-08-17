package com.jamjam.bookjeok.domains.payment.command.repository;

import com.jamjam.bookjeok.domains.payment.command.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}