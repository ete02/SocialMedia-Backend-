package com.kodilla.SocialMediaApp.exceptions.custom.user;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super("Could not find user by: " + message);
    }
}
