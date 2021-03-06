package com.kodilla.SocialMediaApp.exceptions.handler;

import com.kodilla.SocialMediaApp.exceptions.custom.user.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

import static org.springframework.http.HttpHeaders.EMPTY;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class UserErrorAdviceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundHandler(final UserNotFoundException e, final WebRequest webRequest) {
        log.error(Arrays.toString(e.getStackTrace()));
        return handleExceptionInternal(e, e.getMessage(), EMPTY, NOT_FOUND, webRequest);
    }

    @ExceptionHandler(UserNotFoundByIdException.class)
    public ResponseEntity<Object> userNotFoundByIdHandler(final UserNotFoundByIdException e, final WebRequest webRequest) {
        log.error(Arrays.toString(e.getStackTrace()));
        return handleExceptionInternal(e, e.getMessage(), EMPTY, NOT_FOUND, webRequest);
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<Object> userFoundHandler(final UserRegistrationException e, final WebRequest webRequest) {
        log.error(Arrays.toString(e.getStackTrace()));
        return handleExceptionInternal(e, e.getMessage(), EMPTY, BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<Object> userValidationHandler(final UserValidationException e, final WebRequest webRequest) {
        log.error(Arrays.toString(e.getStackTrace()));
        return handleExceptionInternal(e, e.getMessage(), EMPTY, FORBIDDEN, webRequest);
    }

    @ExceptionHandler(UserEnabledException.class)
    public ResponseEntity<Object> userValidationHandler(final UserEnabledException e, final WebRequest webRequest) {
        log.error(Arrays.toString(e.getStackTrace()));
        return handleExceptionInternal(e, e.getMessage(), EMPTY, BAD_REQUEST, webRequest);
    }
}
