package com.example.user_service.auth.application;

import com.example.user_service.auth.application.port.RedisMailRepository;
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
    private final RedisMailRepository redisMailRepository;

    @Value("${email.id}")
    private String emailId;

    private static final String TITLE = "회원 가입 인증 이메일 입니다.";
    private static final String CONTENT = "인증번호는 %s 입니다.";

    public MailService(
            final JavaMailSender emailSender,
            final RedisMailRepository redisMailRepository
    ) {
        this.emailSender = emailSender;
        this.redisMailRepository = redisMailRepository;
    }

    /**
     * 인증 메일을 전송한다
     */
    public void sendAuthenticationEmail(String toEmail) {
        // 메일 세팅
        String randomNumber = generateNumber();
        String setFrom = emailId;
        String toMail = toEmail;
        String title = TITLE;
        String content = CONTENT.formatted(randomNumber);

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

        // 약 3분동안 인증정보를 저장해놓는다.
        redisMailRepository.setDataExpire(toEmail, randomNumber, 180L);
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
