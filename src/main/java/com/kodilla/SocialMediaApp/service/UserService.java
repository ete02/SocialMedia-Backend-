package com.kodilla.SocialMediaApp.service;

import com.kodilla.SocialMediaApp.domain.dto.UserRequest;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.mailboxlayer.exceptions.MailBoxLayerApiException;
import com.kodilla.SocialMediaApp.mailboxlayer.validator.EmailValidator;
import com.kodilla.SocialMediaApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.kodilla.SocialMediaApp.domain.util.Constants.*;

@RequiredArgsConstructor
@Service
public class UserService{
    private final UserServiceDb userServiceDb;
    private final EmailValidator emailValidator;
    private final MailSenderService mailSenderService;
    private final MailCreationService mailCreationService;
    private final PasswordProcessorService passwordProcessorService;

    public User updateUserProfile(final User user, final UserRequest userRequest) {
        User updatedUser = userServiceDb.saveUser(updateUser(user, userRequest));
        sendPersonalizedEmailToUser(updatedUser,
                UPDATE_USER_EMAIL,
                mailCreationService.createUpdateUserProfileEmail(updatedUser));
        return updatedUser;
    }

    public User updateUserPassword(final User user, final String newPassword) {
        if (emailValidator.validateUserEmail(user.getEmail())) {
            String encryptedPassword = passwordProcessorService.encryptPassword(newPassword);
            User userWithUpdatePassword = userServiceDb.saveUser(updatePassword(user, encryptedPassword));
            sendPersonalizedEmailToUser(user,
                    UPDATE_USER_PASSWORD_EMAIL,
                    mailCreationService.createResetPasswordEmail(user, newPassword));
            return userWithUpdatePassword;
        } else {
            throw new MailBoxLayerApiException(user.getEmail());
        }
    }

    public User resetUserPassword(final User user) {
        if (emailValidator.validateUserEmail(user.getEmail())) {
            String password = passwordProcessorService.generateUuid();
            String encryptedPassword = passwordProcessorService.encryptPassword(password);
            User userWithResetPassword = userServiceDb.saveUser(updatePassword(user, encryptedPassword));
            sendPersonalizedEmailToUser(user,
                    RESET_USER_PASSWORD_EMAIL,
                    mailCreationService.createResetPasswordEmail(user, password));
            return userWithResetPassword;
        } else {
            throw new MailBoxLayerApiException(user.getEmail());
        }
    }

    private User updateUser(final User user, final UserRequest userRequest) {
        return user.toBuilder()
                .userName(userRequest.getUserName())
                .email(userRequest.getEmail())
                .description(userRequest.getDescription())
                .updateDate(Instant.now())
                .build();
    }

    private void sendPersonalizedEmailToUser(final User user, final String updateUserEmail, final String updateUserProfileEmail) {
        mailSenderService.sendPersonalizedEmail(user.getEmail(),
                updateUserEmail,
                updateUserProfileEmail);
    }

    private User updatePassword(final User user, final String encryptedPassword) {
        return user.toBuilder().password(encryptedPassword).build();
    }
}