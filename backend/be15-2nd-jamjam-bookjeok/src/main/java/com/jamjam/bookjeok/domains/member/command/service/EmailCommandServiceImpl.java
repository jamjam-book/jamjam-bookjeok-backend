package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.member.command.entity.Member;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailCommandServiceImpl implements EmailCommandService {

    private final JavaMailSender mailSender;

    @Override
    public void sendPasswordResetEmail(Member member, String token) throws MessagingException {
        // 현재 시간
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedNow = now.format(formatter);

        // 비밀번호 재설정 링크
        String resetLink = "http://localhost:5173/password/reset?token=" + token;

        // 이메일 제목
        String subject = "Book적Book적 비밀번호 재설정 메일";

        // 받는 사람 이메일
        String to = member.getEmail();

        // 이메일 전체 구조 정의하는 틀
        String htmlMsg =
                "<div style='font-family: Arial, sans-serif; padding: 20px; background-color: #f4f4f4;'>"
                + "  <div style='max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 30px; border-radius: 10px; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);'>"
                + "    <h2 style='color: #333;'>안녕하세요, Book적Book적입니다! 📚</h2>"
                + "    <p style='font-size: 16px; color: #555;'>회원님은 <strong>" + formattedNow + "</strong>에 비밀번호 재설정을 요청하셨습니다.</p>"
                + "    <p style='font-size: 16px; color: #555;'>아래 버튼을 클릭하셔서 비밀번호를 변경해주세요.</p>"
                + "    <p style='font-size: 16px; color: #555;'>회원 아이디: <strong>" + member.getMemberId() + "</strong></p>"
                + "    <div style='text-align: center; margin: 30px 0;'>"
                + "      <a href='" + resetLink + "' style='display: inline-block; padding: 14px 24px; background-color: #1a73e8; color: white; text-decoration: none; font-weight: bold; border-radius: 6px;'>비밀번호 재설정 하기</a>"
                + "    </div>"
                + "  </div>"
                + "</div>";

        // 메일 전송 로직
        sendEmail(to, subject, htmlMsg);
    }

    public void sendEmail(String to, String subject, String htmlContent) throws MessagingException {
        // html 내용 전송을 도와주는 객체
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
        log.info("이메일을 {}로 전송했습니다. 제목: {}", to, subject);
    }
}
