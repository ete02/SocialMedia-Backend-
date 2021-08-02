package com.kodilla.SocialMediaApp.validator;

import com.kodilla.SocialMediaApp.domain.dto.PasswordRequest;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.service.PasswordProcessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PasswordValidator {
    private final PasswordProcessorService passwordProcessorService;

    public boolean validateNewPasswordWithConfirmed(final PasswordRequest passwordRequest) {
        log.info("Validate that new password and confirmed password are the same!");
        return !passwordRequest.getNewPassword().equals(passwordRequest.getConfirmPassword());
    }

    public boolean validatePasswords(final User user, final PasswordRequest passwordRequest) {
        log.info("Validate that user passed correct passwords!");
        return !passwordRequest.getNewPassword().isBlank() &&
                passwordProcessorService.isEncryptedPasswordMatching(
                        passwordRequest.getCurrentPassword(), user.getPassword());
    }
}
