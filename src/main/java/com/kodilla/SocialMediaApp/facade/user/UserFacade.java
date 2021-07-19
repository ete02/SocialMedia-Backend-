package com.kodilla.SocialMediaApp.facade.user;

import com.kodilla.SocialMediaApp.domain.dto.PasswordRequest;
import com.kodilla.SocialMediaApp.domain.dto.UserDto;
import com.kodilla.SocialMediaApp.domain.dto.UserRequest;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.exceptions.user.UserNotFoundByIdException;
import com.kodilla.SocialMediaApp.exceptions.user.UserNotFoundException;
import com.kodilla.SocialMediaApp.mapper.UserMapper;
import com.kodilla.SocialMediaApp.service.UserService;
import com.kodilla.SocialMediaApp.service.UserServiceDb;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserFacade {
    private final UserMapper userMapper;
    private final UserService userService;
    private final UserServiceDb userServiceDb;
    private final UserFacadeUtils userFacadeUtils;


    public ResponseEntity<List<UserDto>> getUsers() {
        log.info("Get available users!");
        List<UserDto> users = userMapper.mapToUsersDto(userServiceDb.getAllUsers());
        return new ResponseEntity<>(users, OK);
    }

    public ResponseEntity<List<UserDto>> getUsersByLogin(final String login) {
        log.info("Get users contains passed login!");
        List<UserDto> users = userMapper.mapToUsersDto(userServiceDb.getAllUsersByLoginContaining(login));
        return new ResponseEntity<>(users, OK);
    }

    public ResponseEntity<UserDto> getUserById(final Long id) {
        log.info("Get user by id: " + id);
        UserDto userDto = userMapper.mapToUserDto(userServiceDb.getUserById(id)
                .orElseThrow(() -> new UserNotFoundByIdException(id)));
        return new ResponseEntity<>(userDto, OK);
    }

    public ResponseEntity<UserDto> getUserByLogin(final String login) {
        log.info("Get user by login: " + login);
        UserDto userDto = userMapper.mapToUserDto(userServiceDb.getUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login)));
        return new ResponseEntity<>(userDto, OK);
    }

    public ResponseEntity<UserDto> getUserByMail(final String mail) {
        log.info("Get user by mail: " + mail);
        UserDto userDto = userMapper.mapToUserDto(userServiceDb.getUserByEmail(mail)
                .orElseThrow(() -> new UserNotFoundException(mail)));
        return new ResponseEntity<>(userDto, OK);
    }

    public ResponseEntity<UserDto> updateProfile(final UserRequest userRequest) {
        log.info("Try to update user profile!");
        User user = userServiceDb.getUserById(userRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundByIdException(userRequest.getUserId()));
        return userFacadeUtils.updateProfileIfUserIsValidated(userRequest, user);
    }

    public ResponseEntity<String> changePassword(final PasswordRequest passwordRequest) {
        log.info("Try to change user password!");
        User user = userServiceDb.getUserByLogin(passwordRequest.getLogin())
                .orElseThrow(() -> new UserNotFoundException(passwordRequest.getLogin()));
        return userFacadeUtils.changePasswordIfUserPasswordIsValidated(passwordRequest, user);
    }
}
