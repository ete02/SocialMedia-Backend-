package com.kodilla.SocialMediaApp.exceptions.handler;

import com.kodilla.SocialMediaApp.exceptions.custom.role.RoleAssignException;
import com.kodilla.SocialMediaApp.exceptions.custom.role.RoleNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

import static org.springframework.http.HttpHeaders.EMPTY;
import static org.springframework.http.HttpStatus.IM_USED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class RoleErrorAdviceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> roleNotFoundHandler(final RoleNotFoundException e, final WebRequest webRequest) {
        log.error(Arrays.toString(e.getStackTrace()));
        return handleExceptionInternal(e, e.getMessage(), EMPTY, NOT_FOUND, webRequest);
    }

    @ExceptionHandler(RoleAssignException.class)
    public ResponseEntity<Object> roleAssignHandler(final RoleAssignException e, final WebRequest webRequest) {
        log.error(Arrays.toString(e.getStackTrace()));
        return handleExceptionInternal(e, e.getMessage(), EMPTY, IM_USED, webRequest);
    }
}
