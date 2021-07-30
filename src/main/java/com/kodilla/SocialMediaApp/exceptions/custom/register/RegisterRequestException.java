package com.kodilla.SocialMediaApp.exceptions.custom.register;

import com.kodilla.SocialMediaApp.domain.au.RegisterRequest;

public class RegisterRequestException extends RuntimeException{
    public RegisterRequestException(RegisterRequest registerRequest) {
        super(registerRequest + " is not valid!");
    }
}
