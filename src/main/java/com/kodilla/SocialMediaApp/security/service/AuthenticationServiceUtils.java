package com.kodilla.SocialMediaApp.security.service;

import com.kodilla.SocialMediaApp.domain.au.AuthenticationResponse;
import com.kodilla.SocialMediaApp.domain.au.RegisterRequest;
import com.kodilla.SocialMediaApp.domain.entity.Role;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.domain.entity.VerificationToken;
import com.kodilla.SocialMediaApp.domain.enums.RoleType;
import com.kodilla.SocialMediaApp.exceptions.custom.user.UserEnabledException;
import com.kodilla.SocialMediaApp.exceptions.custom.user.UserNotFoundException;
import com.kodilla.SocialMediaApp.security.jwt.JwtProvider;
import com.kodilla.SocialMediaApp.service.PasswordProcessorService;
import com.kodilla.SocialMediaApp.service.RoleServiceDb;
import com.kodilla.SocialMediaApp.service.UserServiceDb;
import com.kodilla.SocialMediaApp.service.VerificationTokenServiceDb;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.kodilla.SocialMediaApp.domain.enums.RoleType.USER;
import static com.kodilla.SocialMediaApp.domain.enums.UserStatus.ACTIVE;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Component
public class AuthenticationServiceUtils {
    private final JwtProvider jwtProvider;
    private final UserServiceDb userServiceDb;
    private final RoleServiceDb roleServiceDb;
    private final PasswordProcessorService passwordProcessorService;
    private final VerificationTokenServiceDb verificationTokenServiceDb;

    public AuthenticationResponse createAuthenticationResponse(final String token, final String refreshToken, final String login) {
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshToken)
                .expiresAt(Instant.now().plusMillis(jwtProvider.jwtExpirationInMillis()))
                .login(login)
                .build();
    }

    public String generateVerificationToken(final User user) {
        String token = passwordProcessorService.generateUuid();
        verificationTokenServiceDb.saveVerificationToken(VerificationToken.builder()
                .token(token)
                .user(user)
                .expirationDate(Instant.now().plusMillis(
                        jwtProvider.jwtExpirationInMillis()))
                .build());
        return token;
    }

    public boolean fetchUserAndEnable(final VerificationToken verificationToken) {
        String login = verificationToken.getUser().getLogin();
        User user = userServiceDb.getUserByLogin(login).orElseThrow(() ->
                new UserNotFoundException(login));
        if (!user.isEnabled()) {
            User enabledUser = user.toBuilder().enabled(true).build();
            assignRole(enabledUser, USER);
            userServiceDb.saveUser(enabledUser);
            log.info("User authenticated correctly!");
        } else {
            throw new UserEnabledException(user.getLogin());
        }
        return true;
    }

    public User assignUserWithRole(final RegisterRequest registerRequest, final RoleType roleType) {
        User user = assignUser(registerRequest);
        assignRole(user, roleType);
        return user;
    }

    private User assignUser(final RegisterRequest registerRequest) {
        return userServiceDb.saveUser(User.builder()
                .login(registerRequest.getLogin())
                .password(passwordProcessorService.encryptPassword(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .city(registerRequest.getCity())
                .description("")
                .createDate(Instant.now())
                .userStatus(ACTIVE)
                .enabled(false)
                .posts(new ArrayList<>())
                .roles(new HashSet<>())
                .build());
    }

    private void assignRole(final User user, final RoleType roleType) {
        roleServiceDb.saveRole(Role.builder()
                .roleType(roleType)
                .users(new HashSet<>(Set.of(user)))
                .build());
    }
}
