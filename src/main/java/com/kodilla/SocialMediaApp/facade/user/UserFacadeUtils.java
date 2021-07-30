package com.kodilla.SocialMediaApp.facade.user;

import com.kodilla.SocialMediaApp.mapper.UserMapper;
import com.kodilla.SocialMediaApp.service.UserService;
import com.kodilla.SocialMediaApp.service.UserServiceDb;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Component
public class UserFacadeUtils {
    private final UserMapper userMapper;
    private final UserService userService;
    private final UserServiceDb userServiceDb;
}