package com.kodilla.SocialMediaApp.validator;

import com.kodilla.SocialMediaApp.service.UserServiceDb;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserValidator {
    private final RoleServiceDb roleServiceDb;
    private final UserServiceDb userServiceDb;
    private final EmailValidator emailValidator;
    private final VerificationTokenServiceDb verificationTokenServiceDb;

