package com.kodilla.SocialMediaApp.facade.user;

import com.kodilla.SocialMediaApp.domain.dto.PasswordRequest;
import com.kodilla.SocialMediaApp.domain.dto.UserDto;
import com.kodilla.SocialMediaApp.domain.dto.UserRequest;
import com.kodilla.SocialMediaApp.domain.entity.Role;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.domain.entity.VerificationToken;
import com.kodilla.SocialMediaApp.exceptions.custom.security.ChangePasswordException;
import com.kodilla.SocialMediaApp.exceptions.custom.security.PasswordNotMatchedException;
import com.kodilla.SocialMediaApp.exceptions.custom.user.UserValidationException;
import com.kodilla.SocialMediaApp.mapper.UserMapper;
import com.kodilla.SocialMediaApp.service.RoleServiceDb;
import com.kodilla.SocialMediaApp.service.UserService;
import com.kodilla.SocialMediaApp.service.UserServiceDb;
import com.kodilla.SocialMediaApp.service.VerificationTokenServiceDb;
import com.kodilla.SocialMediaApp.validator.PasswordValidator;
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
    private final RoleServiceDb roleServiceDb;
    private final UserValidator userValidator;
    private final PasswordValidator passwordValidator;
    private final VerificationTokenServiceDb verificationTokenServiceDb;

    public ResponseEntity<UserDto> updateProfileIfUserIsValidated(final UserRequest userRequest, final User user) {
        if (userValidator.isUserValidateToAssignEmail(user, userRequest)) {
            UserDto userDto = userMapper.mapToUserDto(userService.updateUserProfile(user, userRequest));
            log.info("User profile updated successfully!");
            return new ResponseEntity<>(userDto, OK);
        } else {
            throw new UserValidationException(user.getLogin(), AUTHORIZED_CONTAINS_EMAIL);
        }
    }

    public ResponseEntity<String> changePasswordIfUserPasswordIsValidated(final PasswordRequest passwordRequest, final User user) {
        if (passwordValidator.validateNewPasswordWithConfirmed(passwordRequest)) {
            throw new PasswordNotMatchedException();
        }
        try {
            if (passwordValidator.validatePasswords(user, passwordRequest)) {
                userService.updateUserPassword(user, passwordRequest.getNewPassword());
            } else {
                log.info("Users password changed failed!");
                return new ResponseEntity<>("Current Password is Incorrect", BAD_REQUEST);
            }
            log.info("Users password changed successfully!");
            return new ResponseEntity<>("Password Changed Successfully!", OK);
        } catch (Exception e) {
            throw new ChangePasswordException();
        }
    }

    public ResponseEntity<String> deleteValidatedUser(final User user,
                                                      final List<VerificationToken> verificationTokens,
                                                      final List<Role> roles) {
        if (userValidator.hasUserVerificationToken(user)) {
            verificationTokenServiceDb.deleteVerificationToken(verificationTokens.get(0));
        }
        if (userValidator.hasUserRoles(user)) {
            roles.forEach(role -> {
                role.getUsers().remove(user);
                roleServiceDb.saveRole(role);
            });
        }
        userServiceDb.deleteUser(user);
        log.info("User deleted successfully!");
        return new ResponseEntity<>("User Deleted Successfully!", OK);
    }
}