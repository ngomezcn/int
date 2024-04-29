package com.staxrt.tutorial.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    static private final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    static private final String configPath = rootPath;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        System.out.println("saddddd donde donde donde done done done done done done done done ");


    }

    public void sendEmailFromTemplate(String to, String subject, String htmlTemplate) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        //message.setFrom(new InternetAddress("sender@example.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);

        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
    }


}