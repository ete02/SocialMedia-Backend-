package com.kodilla.SocialMediaApp.exceptions.custom.mail;

public class MailSenderException extends RuntimeException{
    public MailSenderException() {
        super("Failed to process email sending!");
    }
}
