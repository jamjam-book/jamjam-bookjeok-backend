package com.jamjam.bookjeok.domains.payment.repository;

import com.jamjam.bookjeok.domains.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}