package com.karakoc.mezat.config.mailservice;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
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
            String htmlContent = "<html>\n" +
                    "  <style></style>\n" +
                    "  <body>\n" +
                    "    <h2>Tebrikler.!</h2>\n" +
                    "    <br />\n" +
                    "    <h5>"+icerik+"</h5>\n" +
                    "  </body>\n" +
                    "</html>";
            helper.setText(htmlContent, true); // true: HTML içerik olduğunu belirtir
            mailSender.send(mimeMessage);
            return "Gönderildi";
        } catch (MessagingException e) {
            log.info("bir hata");
            e.printStackTrace();
            return "Hata: " + e.getMessage();
        }
    }
}
