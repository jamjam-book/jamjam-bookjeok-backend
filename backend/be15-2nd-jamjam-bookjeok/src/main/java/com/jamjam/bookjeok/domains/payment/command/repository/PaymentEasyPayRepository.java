package com.jamjam.bookjeok.domains.payment.command.repository;

import com.jamjam.bookjeok.domains.payment.command.entity.PaymentEasyPay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentEasyPayRepository extends JpaRepository<PaymentEasyPay, Long> {

}