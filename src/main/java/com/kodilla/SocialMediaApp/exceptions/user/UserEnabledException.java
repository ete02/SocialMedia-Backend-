package com.kodilla.SocialMediaApp.exceptions.user;

public class UserEnabledException extends RuntimeException{
    public UserEnabledException(String login) {
        super("User is already authorized login: " + login);
    }
}
