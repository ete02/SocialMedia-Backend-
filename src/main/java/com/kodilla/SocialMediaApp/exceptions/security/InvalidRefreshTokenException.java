package com.kodilla.SocialMediaApp.exceptions.security;

public class InvalidRefreshTokenException extends RuntimeException{
    public InvalidRefreshTokenException(String token) {
        super("Invalid refresh token passed: " + token);
    }
}
