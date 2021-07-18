package com.kodilla.SocialMediaApp.exceptions.user;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super("Could not find user by: " + message);
    }
}
