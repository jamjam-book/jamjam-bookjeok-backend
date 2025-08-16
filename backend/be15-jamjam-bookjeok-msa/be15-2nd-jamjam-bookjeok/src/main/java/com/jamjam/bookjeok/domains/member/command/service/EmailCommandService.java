package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.member.command.entity.Member;
import jakarta.mail.MessagingException;

public interface EmailCommandService {
    void sendPasswordResetEmail(Member member, String token) throws MessagingException;

    void sendEmail(String to, String subject, String htmlContent) throws MessagingException;
}
