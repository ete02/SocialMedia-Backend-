package com.kodilla.SocialMediaApp.exceptions.custom.security;

public class ChangePasswordException extends RuntimeException{
    public ChangePasswordException() {
        super("Error during changing password process!");
    }
}
