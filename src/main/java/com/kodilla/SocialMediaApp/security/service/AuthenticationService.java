package com.kodilla.SocialMediaApp.security.service;

import com.kodilla.SocialMediaApp.domain.au.AuthenticationResponse;
import com.kodilla.SocialMediaApp.domain.au.LoginRequest;
import com.kodilla.SocialMediaApp.domain.au.RefreshTokenRequest;
import com.kodilla.SocialMediaApp.domain.au.RegisterRequest;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.domain.entity.VerificationToken;
import com.kodilla.SocialMediaApp.exceptions.custom.register.RegisterRequestException;
import com.kodilla.SocialMediaApp.exceptions.custom.security.VerificationTokenNotFoundException;
import com.kodilla.SocialMediaApp.exceptions.custom.user.UserRegistrationException;
import com.kodilla.SocialMediaApp.mailboxlayer.validator.EmailValidator;
import com.kodilla.SocialMediaApp.security.jwt.JwtProvider;
import com.kodilla.SocialMediaApp.service.*;
import com.kodilla.SocialMediaApp.validator.ValidateRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.kodilla.SocialMediaApp.domain.enums.RoleType.NO_ROLE;
import static com.kodilla.SocialMediaApp.domain.util.Constants.NEW_USER_EMAIL;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthenticationService {
    private final JwtProvider jwtProvider;
    private final ImageService imageService;
    private final UserServiceDb userServiceDb;
    private final EmailValidator emailValidator;
    private final MailSenderService mailSenderService;
    private final RefreshTokenService refreshTokenService;
    private final MailCreationService mailCreationService;
    private final AuthenticationManager authenticationManager;
    private final ValidateRegisterRequest validateRegisterRequest;
    private final VerificationTokenServiceDb verificationTokenServiceDb;
    private final AuthenticationServiceUtils authenticationServiceUtils;

    public boolean register(final RegisterRequest registerRequest) {
        boolean isRegisterRequestValid = validateRegisterRequest.isRegisterRequestValid(registerRequest);
        List<User> users = userServiceDb.getAllUsersByLoginContaining(registerRequest.getLogin());
        if (isRegisterRequestValid && users.isEmpty() && (emailValidator.validateUserEmail(registerRequest.getEmail()))) {
            User user = authenticationServiceUtils.assignUserWithRole(registerRequest, NO_ROLE);
            String token = authenticationServiceUtils.generateVerificationToken(user);
            imageService.loadDefaultUserImage(user);
            mailSenderService.sendPersonalizedEmail(user.getEmail(), NEW_USER_EMAIL,
                    mailCreationService.createNewUserEmail(user, token));
        } else {
            throwCustomExceptions(registerRequest, isRegisterRequestValid);
        }
        return true;
    }

    public boolean verifyToken(final String token) {
        VerificationToken verificationToken = verificationTokenServiceDb.getVerificationTokenByToken(token)
                .orElseThrow(() -> new VerificationTokenNotFoundException(token));
        return authenticationServiceUtils.fetchUserAndEnable(verificationToken);
    }

    public AuthenticationResponse login(final LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return authenticationServiceUtils.createAuthenticationResponse(
                token,
                refreshTokenService.generateRefreshToken().getToken(),
                loginRequest.getLogin());
    }

    public AuthenticationResponse refreshToken(final RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithLogin(refreshTokenRequest.getLogin());
        return authenticationServiceUtils.createAuthenticationResponse(
                token,
                refreshTokenRequest.getRefreshToken(),
                refreshTokenRequest.getLogin());
    }

    private void throwCustomExceptions(final RegisterRequest registerRequest, final boolean isRegisterRequestValid) {
        if (!isRegisterRequestValid) {
            throw new RegisterRequestException(registerRequest);
        } else {
            throw new UserRegistrationException(registerRequest.getLogin(), registerRequest.getEmail());
        }
    }
}
