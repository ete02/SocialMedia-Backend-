package com.kodilla.SocialMediaApp.exceptions.custom.security;

public class PasswordNotMatchedException extends RuntimeException{
    public PasswordNotMatchedException() {
        super("New password and confirmed password not matched!");
    }
}
