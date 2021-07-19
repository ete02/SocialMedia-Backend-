package com.kodilla.SocialMediaApp.facade.user;

import com.kodilla.SocialMediaApp.domain.dto.PasswordRequest;
import com.kodilla.SocialMediaApp.domain.dto.UserDto;
import com.kodilla.SocialMediaApp.domain.dto.UserRequest;
import com.kodilla.SocialMediaApp.domain.entity.Role;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.domain.entity.VerificationToken;
import com.kodilla.SocialMediaApp.exceptions.user.UserValidationException;
import com.kodilla.SocialMediaApp.mapper.UserMapper;
import com.kodilla.SocialMediaApp.service.UserService;
import com.kodilla.SocialMediaApp.service.UserServiceDb;
import com.kodilla.SocialMediaApp.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import java.util.List;

import static com.kodilla.SocialMediaApp.domain.enums.ValidationStatus.AUTHORIZED_CONTAINS_EMAIL;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Component
public class UserFacadeUtils {
    private final UserMapper userMapper;
    private final UserService userService;
    private final UserServiceDb userServiceDb;
}