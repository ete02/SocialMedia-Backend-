package com.kodilla.SocialMediaApp.service;

import com.kodilla.SocialMediaApp.domain.mail.Mail;
import com.kodilla.SocialMediaApp.exceptions.custom.mail.MailSenderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailSenderService {
    private final MailCreationService mailCreationService;
    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail(final Mail mail) {
        log.info("Starting email preparation...");
        try {
            javaMailSender.send(mailCreationService.createMimeMessage(mail));
            log.info("Email has been sent.");
        } catch (MailException | MailSenderException e) {
            throw new MailSenderException();
        }
    }

    public void sendPersonalizedEmail(final String email, final String subject, final String text) {
        sendEmail(mailCreationService.createMail(email, subject, text));
    }
}
