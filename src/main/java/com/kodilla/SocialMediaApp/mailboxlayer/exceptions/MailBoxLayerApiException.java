package com.kodilla.SocialMediaApp.mailboxlayer.exceptions;

public class MailBoxLayerApiException extends RuntimeException{
    public MailBoxLayerApiException(String email) {
        super("MailBoxLayerApi error for validation of email: " + email);
    }
}
