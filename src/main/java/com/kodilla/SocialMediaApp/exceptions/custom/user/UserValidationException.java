package com.kodilla.SocialMediaApp.exceptions.custom.user;

import com.kodilla.SocialMediaApp.domain.enums.ValidationStatus;

public class UserValidationException extends RuntimeException{
    public UserValidationException(String login, ValidationStatus validationStatus) {
        super("User " + login + validationStatus.toString());
    }
}
