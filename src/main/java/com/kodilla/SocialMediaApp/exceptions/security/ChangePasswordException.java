package com.kodilla.SocialMediaApp.exceptions.security;

public class ChangePasswordException extends RuntimeException{
    public ChangePasswordException() {
        super("Error during changing password process!");
    }
}
