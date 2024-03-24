package com.karakoc.mezat.config.mailservice;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailManager implements MailService {
    private JavaMailSender mailSender;

    @Autowired
    public MailManager(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public String sendMail(String to, String icerik) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setFrom("shopifyemirhan6@gmail.com");
            helper.setTo(to);
            helper.setSubject("Müzayede kazandınız.!");
            String htmlContent = "<div>" +
                    "<h1>Tebrikler</h1>" +
                    "<div>" +
                    icerik +
                    "</div>" +
                    "</div>";
            helper.setText(htmlContent, true); // true: HTML içerik olduğunu belirtir
            mailSender.send(mimeMessage);
            return "Gönderildi";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Hata: " + e.getMessage();
        }
    }
}
