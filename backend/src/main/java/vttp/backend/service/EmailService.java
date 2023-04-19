package vttp.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    @Autowired
    private  JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendEmail (String toEmail, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("roysoonth@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

        logger.info(">>>> Email sent to: " + toEmail);


    }
    
}
