package com.karakoc.mezat.config.mailservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service

public class MailManager implements MailService{
    private  JavaMailSender mailSender;

    @Autowired
    public MailManager(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public String sendMail(String to, String icerik) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("shopifyemirhan6@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText("<div>" +
                "<h1>Tebrikler</h1>" +
                "<div>" +
                icerik +
                //Ürün 275 tlye emirhan karakoc kişisine satılmıştır. Hadi hayırlı olsun.
                "</div>" +
                "</div>");
        simpleMailMessage.setSubject("Müzayede kazandınız.!");
        mailSender.send(simpleMailMessage);



        return "Gonderildi";
    }


}
