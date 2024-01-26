package com.example.reservation_purchase.auth.application;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MailService {

    private final JavaMailSender emailSender;

    @Value("${email.id}")
    private String emailId;

    private static final String TITLE = "회원 가입 인증 이메일 입니다.";
    private static final String CONTENT = "인증번호는 %s 입니다.";

    public MailService(final JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendAuthenticationEmail(String toEmail) {
        // 메일 세팅
        String setFrom = emailId;
        String toMail = toEmail;
        String title = TITLE;
        String content = CONTENT.formatted(generateNumber());

        // 메일 전송
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        // TODO : redis 에 {email, 마감최종시간, 비밀번호} 를 저장한다.
    }

    /*
      인증에 사용할 6자리 난수 생성
    */
    private String generateNumber() {
        Random random = new Random();

        return IntStream.range(0, 6)
                .mapToObj(i -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining());
    }
}
